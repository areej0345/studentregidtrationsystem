package com.student;

import com.student.ui.StudentMenu;
import com.student.util.DBConnection;

/**
 * Entry point for the Student Registration System.
 *
 * Run steps:
 *   1. Set up MySQL: run sql/schema.sql
 *   2. Update credentials in resources/db.properties
 *   3. Add mysql-connector-j-x.x.x.jar to /lib/
 *   4. Compile:  javac -cp lib/mysql-connector-j-x.x.x.jar -d out \
 *                  src/com/student/util/DBConnection.java \
 *                  src/com/student/model/Student.java \
 *                  src/com/student/dao/StudentDAO.java \
 *                  src/com/student/dao/StudentDAOImpl.java \
 *                  src/com/student/ui/StudentMenu.java \
 *                  src/com/student/Main.java
 *   5. Run:      java -cp "out:lib/mysql-connector-j-x.x.x.jar:resources" com.student.Main
 *      (Windows) java -cp "out;lib\mysql-connector-j-x.x.x.jar;resources" com.student.Main
 */
public class Main {

    public static void main(String[] args) {
        // Test DB connection before launching the menu
        System.out.println("Connecting to database...");
        if (!DBConnection.testConnection()) {
            System.err.println("Startup aborted. Please check db.properties.");
            System.exit(1);
        }

        // Launch the console UI
        new StudentMenu().start();
    }
}