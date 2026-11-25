# 🎓 Student Registration System

A Java-based Student Registration System designed to manage student information efficiently using a MySQL database. The project provides a foundation for organizing student records in a structured and centralized database.

## 📌 Project Overview

The Student Registration System is developed to simplify the process of maintaining student records. It uses Java for application development and MySQL for storing student information.

The database stores essential student details, including names, email addresses, phone numbers, courses, and ages.

## ✨ Key Features

- **Student Registration:** Store student information in a database.
- **Student Information Management:** Maintain organized student records.
- **Unique Email:** Prevent duplicate email addresses in the database.
- **Course Information:** Store the course associated with each student.
- **Automatic Student ID:** Generate a unique ID for every student.
- **Registration Timestamp:** Automatically record when a student is registered.
- **Record Update Timestamp:** Track when student records are modified.

## 🛠️ Technologies Used

| Technology | Purpose |
|---|---|
| Java | Application development |
| MySQL | Database management |
| SQL | Database creation and queries |
| JDBC | Java database connectivity, if configured in the application |

## 📂 Project Structure

```text
studentregidtrationsystem/
│
├── StudentRegistrationSystem/
│   └── src/
│       └── com/
│           └── student/
│               └── Java source files
│
├── student registration project.sql
│
└── README.md
```

## 🗄️ Database Structure

**Database Name:** `student_registration`

**Table Name:** `students`

| Column | Data Type | Description |
|---|---|---|
| id | INT | Primary key, auto-increment |
| name | VARCHAR(100) | Student name |
| email | VARCHAR(150) | Student email (unique) |
| phone | VARCHAR(15) | Contact number |
| course | VARCHAR(100) | Enrolled course |
| age | INT | Student age (16–100) |
| created_at | TIMESTAMP | Registration date |
| updated_at | TIMESTAMP | Last update date |

### Database Features

- Auto-increment primary key.
- Unique constraint on email.
- Age validation between 16 and 100.
- Automatic registration timestamp.
- Automatic update timestamp.

## ⚙️ Installation and Setup

### 1. Clone the Repository

```bash
git clone https://github.com/areej0345/studentregidtrationsystem.git
```

### 2. Open the Project

Open the downloaded project in your preferred Java IDE, such as:

- IntelliJ IDEA
- Eclipse
- NetBeans

### 3. Install Prerequisites

Make sure you have installed:

- Java Development Kit (JDK)
- MySQL Server
- MySQL Workbench (optional)
- A Java IDE

### 4. Configure the Database

Open MySQL Workbench and execute the provided SQL file:

`student registration project.sql`

Alternatively, run:

```sql
CREATE DATABASE IF NOT EXISTS student_registration;

USE student_registration;

CREATE TABLE IF NOT EXISTS students (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(150) NOT NULL UNIQUE,
    phone VARCHAR(15),
    course VARCHAR(100) NOT NULL,
    age INT CHECK (age >= 16 AND age <= 100),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
    ON UPDATE CURRENT_TIMESTAMP
);
```

### 5. Configure Database Connection

If the application uses JDBC, configure the connection with your MySQL credentials.

Example:

```java
String url = "jdbc:mysql://localhost:3306/student_registration";
String username = "root";
String password = "your_password";
```

Make sure the MySQL JDBC Connector is available in your project.

### 6. Run the Application

1. Open the project in your Java IDE.
2. Locate the main Java class.
3. Configure the database connection.
4. Build and run the project.

## 🎯 Project Objectives

- Simplify student information management.
- Maintain structured student records.
- Reduce manual record-keeping.
- Practice Java programming and database integration.
- Understand relational database design and SQL constraints.

## 🔮 Future Enhancements

- Add a graphical user interface.
- Implement student search functionality.
- Add update and delete operations.
- Implement student record validation.
- Add login and authentication.
- Generate student registration reports.
- Improve database security.

---

*Developed as a Java and MySQL database management project.*
