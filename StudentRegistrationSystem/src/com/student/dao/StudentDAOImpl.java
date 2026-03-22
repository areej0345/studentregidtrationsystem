
package com.student.dao;

import com.student.model.Student;
import com.student.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * JDBC implementation of StudentDAO.
 * All SQL operations use PreparedStatements to prevent SQL injection.
 */
public class StudentDAOImpl implements StudentDAO {

    // --- SQL Queries (constants for maintainability) ---
    private static final String INSERT_SQL =
        "INSERT INTO students (name, email, phone, course, age) VALUES (?, ?, ?, ?, ?)";

    private static final String SELECT_BY_ID =
        "SELECT * FROM students WHERE id = ?";

    private static final String SELECT_BY_EMAIL =
        "SELECT * FROM students WHERE email = ?";

    private static final String SELECT_ALL =
        "SELECT * FROM students ORDER BY created_at DESC";

    private static final String SEARCH_BY_NAME =
        "SELECT * FROM students WHERE name LIKE ? ORDER BY name";

    private static final String UPDATE_SQL =
        "UPDATE students SET name=?, email=?, phone=?, course=?, age=? WHERE id=?";

    private static final String DELETE_SQL =
        "DELETE FROM students WHERE id=?";

    private static final String COUNT_SQL =
        "SELECT COUNT(*) FROM students";


    // ==================== CREATE ====================

    @Override
    public int addStudent(Student student) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(INSERT_SQL,
                                        Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, student.getName());
            ps.setString(2, student.getEmail());
            ps.setString(3, student.getPhone());
            ps.setString(4, student.getCourse());
            ps.setInt   (5, student.getAge());

            int affected = ps.executeUpdate();

            if (affected > 0) {
                try (ResultSet keys = ps.getGeneratedKeys()) {
                    if (keys.next()) {
                        int generatedId = keys.getInt(1);
                        student.setId(generatedId);
                        return generatedId;
                    }
                }
            }
        } catch (SQLIntegrityConstraintViolationException e) {
            System.err.println("[ERROR] Email already exists: " + student.getEmail());
        } catch (SQLException e) {
            System.err.println("[ERROR] addStudent failed: " + e.getMessage());
        }
        return -1;
    }


    // ==================== READ ====================

    @Override
    public Student getStudentById(int id) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SELECT_BY_ID)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapRow(rs);
            }
        } catch (SQLException e) {
            System.err.println("[ERROR] getStudentById failed: " + e.getMessage());
        }
        return null;
    }

    @Override
    public Student getStudentByEmail(String email) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SELECT_BY_EMAIL)) {

            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapRow(rs);
            }
        } catch (SQLException e) {
            System.err.println("[ERROR] getStudentByEmail failed: " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<Student> getAllStudents() {
        List<Student> list = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SELECT_ALL);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) list.add(mapRow(rs));

        } catch (SQLException e) {
            System.err.println("[ERROR] getAllStudents failed: " + e.getMessage());
        }
        return list;
    }

    @Override
    public List<Student> searchStudentsByName(String name) {
        List<Student> list = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SEARCH_BY_NAME)) {

            ps.setString(1, "%" + name + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) list.add(mapRow(rs));
            }
        } catch (SQLException e) {
            System.err.println("[ERROR] searchStudentsByName failed: " + e.getMessage());
        }
        return list;
    }


    // ==================== UPDATE ====================

    @Override
    public boolean updateStudent(Student student) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(UPDATE_SQL)) {

            ps.setString(1, student.getName());
            ps.setString(2, student.getEmail());
            ps.setString(3, student.getPhone());
            ps.setString(4, student.getCourse());
            ps.setInt   (5, student.getAge());
            ps.setInt   (6, student.getId());

            return ps.executeUpdate() > 0;

        } catch (SQLIntegrityConstraintViolationException e) {
            System.err.println("[ERROR] Email already in use by another student.");
        } catch (SQLException e) {
            System.err.println("[ERROR] updateStudent failed: " + e.getMessage());
        }
        return false;
    }


    // ==================== DELETE ====================

    @Override
    public boolean deleteStudent(int id) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(DELETE_SQL)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("[ERROR] deleteStudent failed: " + e.getMessage());
        }
        return false;
    }


    // ==================== UTILITY ====================

    @Override
    public int getTotalStudentCount() {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(COUNT_SQL);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) return rs.getInt(1);

        } catch (SQLException e) {
            System.err.println("[ERROR] getTotalStudentCount failed: " + e.getMessage());
        }
        return 0;
    }

    /** Maps a ResultSet row to a Student object. */
    private Student mapRow(ResultSet rs) throws SQLException {
        return new Student(
            rs.getInt      ("id"),
            rs.getString   ("name"),
            rs.getString   ("email"),
            rs.getString   ("phone"),
            rs.getString   ("course"),
            rs.getInt      ("age"),
            rs.getTimestamp("created_at"),
            rs.getTimestamp("updated_at")
        );
    }
}