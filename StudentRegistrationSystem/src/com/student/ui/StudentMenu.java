package com.student.ui;

import com.student.dao.StudentDAO;
import com.student.dao.StudentDAOImpl;
import com.student.model.Student;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

/**
 * Console-based menu interface for the Student Registration System.
 * Handles all user input/output and delegates to StudentDAO for data operations.
 */
public class StudentMenu {

    private final StudentDAO dao;
    private final Scanner    scanner;

    private static final String DIVIDER =
        "+------+----------------------+---------------------------+---------------+------------------------+-----+";
    private static final String HEADER  =
        "| ID   | Name                 | Email                     | Phone         | Course                 | Age |";

    public StudentMenu() {
        this.dao     = new StudentDAOImpl();
        this.scanner = new Scanner(System.in);
    }

    /** Main menu loop. */
    public void start() {
        System.out.println("\n╔══════════════════════════════════════╗");
        System.out.println("║   Student Registration System v1.0   ║");
        System.out.println("╚══════════════════════════════════════╝");

        boolean running = true;
        while (running) {
            printMenu();
            int choice = readInt("Enter choice: ");
            System.out.println();

            switch (choice) {
                case 1  -> addStudent();
                case 2  -> viewAllStudents();
                case 3  -> searchStudent();
                case 4  -> updateStudent();
                case 5  -> deleteStudent();
                case 6  -> viewStudentById();
                case 0  -> { running = false; System.out.println("Goodbye!"); }
                default -> System.out.println("[!] Invalid choice. Try again.");
            }
        }
        scanner.close();
    }

    // ─── Menu Options ─────────────────────────────────────────────────────────

    private void printMenu() {
        System.out.println("\n┌─────────────────────────────┐");
        System.out.println("│         MAIN MENU           │");
        System.out.println("├─────────────────────────────┤");
        System.out.println("│  1. Add New Student         │");
        System.out.println("│  2. View All Students       │");
        System.out.println("│  3. Search Student by Name  │");
        System.out.println("│  4. Update Student          │");
        System.out.println("│  5. Delete Student          │");
        System.out.println("│  6. Find Student by ID      │");
        System.out.println("│  0. Exit                    │");
        System.out.println("└─────────────────────────────┘");
    }

    // ─── ADD ──────────────────────────────────────────────────────────────────

    private void addStudent() {
        System.out.println("── Add New Student ──");
        String name   = readString("Name:   ");
        String email  = readString("Email:  ");
        String phone  = readString("Phone:  ");
        String course = readString("Course: ");
        int    age    = readInt   ("Age:    ");

        // Basic validation
        if (name.isBlank() || email.isBlank() || course.isBlank()) {
            System.out.println("[!] Name, Email, and Course are required.");
            return;
        }
        if (!email.contains("@")) {
            System.out.println("[!] Enter a valid email address.");
            return;
        }
        if (age < 16 || age > 100) {
            System.out.println("[!] Age must be between 16 and 100.");
            return;
        }

        Student s = new Student(name, email, phone, course, age);
        int id = dao.addStudent(s);

        if (id > 0) {
            System.out.println("[✓] Student added successfully! ID = " + id);
        } else {
            System.out.println("[✗] Failed to add student (email may already exist).");
        }
    }

    // ─── VIEW ALL ─────────────────────────────────────────────────────────────

    private void viewAllStudents() {
        List<Student> students = dao.getAllStudents();

        if (students.isEmpty()) {
            System.out.println("[i] No students registered yet.");
            return;
        }

        System.out.println("\nTotal students: " + students.size());
        printTableHeader();
        students.forEach(s -> System.out.println(s));
        System.out.println(DIVIDER);
    }

    // ─── SEARCH ───────────────────────────────────────────────────────────────

    private void searchStudent() {
        String name = readString("Search by name: ");
        List<Student> results = dao.searchStudentsByName(name);

        if (results.isEmpty()) {
            System.out.println("[i] No students found matching \"" + name + "\".");
        } else {
            System.out.println("Found " + results.size() + " result(s):");
            printTableHeader();
            results.forEach(s -> System.out.println(s));
            System.out.println(DIVIDER);
        }
    }

    // ─── UPDATE ───────────────────────────────────────────────────────────────

    private void updateStudent() {
        int id = readInt("Enter Student ID to update: ");
        Student existing = dao.getStudentById(id);

        if (existing == null) {
            System.out.println("[!] No student found with ID " + id);
            return;
        }

        System.out.println("Current: " + existing);
        System.out.println("(Press Enter to keep current value)\n");

        String name   = readStringOrDefault("New Name   [" + existing.getName()   + "]: ", existing.getName());
        String email  = readStringOrDefault("New Email  [" + existing.getEmail()  + "]: ", existing.getEmail());
        String phone  = readStringOrDefault("New Phone  [" + existing.getPhone()  + "]: ", existing.getPhone());
        String course = readStringOrDefault("New Course [" + existing.getCourse() + "]: ", existing.getCourse());
        String ageStr = readStringOrDefault("New Age    [" + existing.getAge()    + "]: ", String.valueOf(existing.getAge()));

        int newAge;
        try {
            newAge = Integer.parseInt(ageStr.trim());
        } catch (NumberFormatException e) {
            System.out.println("[!] Invalid age. Update cancelled.");
            return;
        }

        existing.setName(name);
        existing.setEmail(email);
        existing.setPhone(phone);
        existing.setCourse(course);
        existing.setAge(newAge);

        if (dao.updateStudent(existing)) {
            System.out.println("[✓] Student updated successfully.");
        } else {
            System.out.println("[✗] Update failed.");
        }
    }

    // ─── DELETE ───────────────────────────────────────────────────────────────

    private void deleteStudent() {
        int id = readInt("Enter Student ID to delete: ");
        Student s = dao.getStudentById(id);

        if (s == null) {
            System.out.println("[!] No student found with ID " + id);
            return;
        }

        System.out.println("About to delete: " + s.getName() + " (" + s.getEmail() + ")");
        String confirm = readString("Are you sure? (yes/no): ");

        if (confirm.equalsIgnoreCase("yes")) {
            if (dao.deleteStudent(id)) {
                System.out.println("[✓] Student deleted.");
            } else {
                System.out.println("[✗] Delete failed.");
            }
        } else {
            System.out.println("Delete cancelled.");
        }
    }

    // ─── FIND BY ID ───────────────────────────────────────────────────────────

    private void viewStudentById() {
        int id = readInt("Enter Student ID: ");
        Student s = dao.getStudentById(id);

        if (s == null) {
            System.out.println("[!] No student found with ID " + id);
        } else {
            printTableHeader();
            System.out.println(s);
            System.out.println(DIVIDER);
            System.out.println("Registered: " + s.getCreatedAt());
        }
    }

    // ─── Helpers ──────────────────────────────────────────────────────────────

    private void printTableHeader() {
        System.out.println(DIVIDER);
        System.out.println(HEADER);
        System.out.println(DIVIDER);
    }

    private String readString(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    private String readStringOrDefault(String prompt, String defaultVal) {
        System.out.print(prompt);
        String input = scanner.nextLine().trim();
        return input.isEmpty() ? defaultVal : input;
    }

    private int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                int val = scanner.nextInt();
                scanner.nextLine(); // consume newline
                return val;
            } catch (InputMismatchException e) {
                scanner.nextLine(); // clear bad input
                System.out.println("[!] Please enter a valid number.");
            }
        }
    }
}