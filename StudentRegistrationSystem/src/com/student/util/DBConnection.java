package com.student.util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Utility class for managing the JDBC database connection.
 * Reads credentials from resources/db.properties (keeps them out of source code).
 */
public class DBConnection {

    private static String url;
    private static String username;
    private static String password;

    // Load driver and properties once at class load time
    static {
        try (InputStream input = DBConnection.class
                .getClassLoader()
                .getResourceAsStream("db.properties")) {

            if (input == null) {
                throw new RuntimeException(
                    "db.properties not found in classpath (resources/)");
            }

            Properties props = new Properties();
            props.load(input);

            String driver = props.getProperty("db.driver");
            url      = props.getProperty("db.url");
            username = props.getProperty("db.username");
            password = props.getProperty("db.password");

            // Explicitly load driver (required for some environments)
            Class.forName(driver);

        } catch (IOException e) {
            throw new RuntimeException("Failed to load db.properties: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("MySQL JDBC driver not found. Add the JAR to /lib/");
        }
    }

    /**
     * Opens and returns a new database connection.
     * Caller is responsible for closing the connection (use try-with-resources).
     *
     * @return a live Connection object
     * @throws SQLException if the connection cannot be established
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }

    /**
     * Tests the database connection and prints the result.
     * Useful for quick connectivity checks during setup.
     */
    public static boolean testConnection() {
        try (Connection conn = getConnection()) {
            System.out.println("[OK] Database connected: " + conn.getMetaData().getURL());
            return true;
        } catch (SQLException e) {
            System.err.println("[FAIL] Cannot connect to database: " + e.getMessage());
            return false;
        }
    }
}