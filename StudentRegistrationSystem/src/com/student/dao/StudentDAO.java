package com.student.dao;

import com.student.model.Student;
import java.util.List;

/**
 * Data Access Object interface for Student CRUD operations.
 * Separates business logic from database implementation.
 */
public interface StudentDAO {

    /** Insert a new student. Returns generated ID, or -1 on failure. */
    int addStudent(Student student);

    /** Retrieve a student by their ID. Returns null if not found. */
    Student getStudentById(int id);

    /** Retrieve a student by email. Returns null if not found. */
    Student getStudentByEmail(String email);

    /** Retrieve all students. Returns empty list if none exist. */
    List<Student> getAllStudents();

    /** Search students by name (partial match). */
    List<Student> searchStudentsByName(String name);

    /** Update an existing student's details. Returns true if successful. */
    boolean updateStudent(Student student);

    /** Delete a student by ID. Returns true if successful. */
    boolean deleteStudent(int id);

    /** Returns total count of students in the database. */
    int getTotalStudentCount();
}