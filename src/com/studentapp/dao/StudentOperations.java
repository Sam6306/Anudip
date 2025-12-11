package com.studentapp.dao;

import com.studentapp.db.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class StudentOperations {

    public boolean addStudent(int roll, String name, String cls) {
        String sql = "INSERT INTO students (roll_no, name, student_class) VALUES (?, ?, ?)";


        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, roll);
            ps.setString(2, name);
            ps.setString(3, cls);

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
            return false;
        }
    }

    public boolean addMarks(int roll, String sub, int marks) {
        String sql = "INSERT INTO marks (roll_no, subject_name, marks) VALUES (?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, roll);
            ps.setString(2, sub);
            ps.setInt(3, marks);

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
            return false;
        }
    }
}
