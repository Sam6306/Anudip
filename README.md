📘 Student Tracker Application

A desktop application built using Java Swing, MySQL, and JDBC to manage students, add marks, view reports, and perform CRUD operations.
The application provides a simple and user-friendly interface for colleges or institutes.

🚀 Features

➕ Add new students

❌ Delete existing students

📄 View all students

📊 View detailed student reports

🔗 MySQL database connectivity using JDBC

🧭 User-friendly Swing-based GUI

📁 Modular project structure (DAO, DB, UI packages)

🛠️ Technologies Used

Java (Core + OOP)

Swing GUI

MySQL Database

JDBC (Java Database Connectivity)

📂 Project Structure
src/
└── com.studentapp/
    ├── dao/
    │   └── StudentOperations.java     # Handles student-related DB operations (insert, delete, fetch)
    │
    ├── db/
    │   ├── DBConnection.java          # MySQL connection setup
    │   └── DBTest.java                # Test file to check DB connection (optional)
    │
    ├── ui/
    │   ├── AddStudentFrame.java       # GUI to add a new student
    │   ├── DeleteStudentFrame.java    # GUI to delete a student by roll number
    │   ├── ViewStudentsFrame.java     # GUI to view all students
    │   ├── ViewReportFrame.java       # GUI to view a student's marks & report
    │   ├── MainMenu.java              # Main menu window of the application
    │   └── Iconn.png                  # App icon image used in UI

🗄️ Database Setup
1️⃣ Create Database
CREATE DATABASE student_db;
USE student_db;

2️⃣ Students Table
CREATE TABLE students (
    roll_no INT PRIMARY KEY,
    name VARCHAR(50),
    student_class VARCHAR(20)
);

3️⃣ Marks Table 
CREATE TABLE marks (
    id INT AUTO_INCREMENT PRIMARY KEY,
    roll_no INT,
    subject_name VARCHAR(100),
    marks INT,
    FOREIGN KEY (roll_no) REFERENCES students(roll_no)
);

▶️ How to Run the Project

Install Java JDK (17+ recommended)

Install MySQL

Open the project in IntelliJ IDEA

Add the MySQL Connector JAR to your project libraries

Update database credentials in:

src/com/studentapp/db/DBConnection.java


Run:

MainMenu.java


(This is the entry point for your GUI application.)
