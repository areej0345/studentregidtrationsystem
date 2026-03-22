package com.student.model;

import java.sql.Timestamp;

/**
 * Represents a student entity matching the 'students' database table.
 */
public class Student {

    private int       id;
    private String    name;
    private String    email;
    private String    phone;
    private String    course;
    private int       age;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    // --- Constructors ---

    public Student() {}

    /** Constructor for creating a new student (no ID yet). */
    public Student(String name, String email, String phone, String course, int age) {
        this.name   = name;
        this.email  = email;
        this.phone  = phone;
        this.course = course;
        this.age    = age;
    }

    /** Full constructor (used when reading from DB). */
    public Student(int id, String name, String email, String phone,
                   String course, int age, Timestamp createdAt, Timestamp updatedAt) {
        this.id        = id;
        this.name      = name;
        this.email     = email;
        this.phone     = phone;
        this.course    = course;
        this.age       = age;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // --- Getters and Setters ---

    public int       getId()        { return id; }
    public void      setId(int id)  { this.id = id; }

    public String    getName()           { return name; }
    public void      setName(String n)   { this.name = n; }

    public String    getEmail()          { return email; }
    public void      setEmail(String e)  { this.email = e; }

    public String    getPhone()          { return phone; }
    public void      setPhone(String p)  { this.phone = p; }

    public String    getCourse()         { return course; }
    public void      setCourse(String c) { this.course = c; }

    public int       getAge()            { return age; }
    public void      setAge(int a)       { this.age = a; }

    public Timestamp getCreatedAt()      { return createdAt; }
    public Timestamp getUpdatedAt()      { return updatedAt; }

    @Override
    public String toString() {
        return String.format(
            "| %-4d | %-20s | %-25s | %-13s | %-22s | %-3d |",
            id, name, email, phone, course, age
        );
    }
}