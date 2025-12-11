package com.studentapp.ui;

import com.studentapp.db.DBConnection;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.sql.*;

public class ViewReportFrame extends JFrame {

    private JTable table;
    private JTextField rollField;

    public ViewReportFrame() {
        setTitle("Student Report");
        setSize(600, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(new Color(50, 50, 50));

        ImageIcon icon = new ImageIcon("src/com/studentapp/ui/Iconn.png");
        setIconImage(icon.getImage());

        JPanel titleBar = new JPanel();
        titleBar.setOpaque(true);
        titleBar.setBackground(new Color(10, 10, 10));
        titleBar.setBorder(BorderFactory.createEmptyBorder(5, 0, 10, 0));

        JLabel title = new JLabel("STUDENT REPORT");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Times New Roman", Font.BOLD, 22));
        titleBar.add(title);

        add(titleBar, BorderLayout.NORTH);

        JPanel topPanel = new JPanel();
        topPanel.setBackground(new Color(50, 50, 50));

        JLabel lbl = new JLabel("Enter Roll No:");
        lbl.setForeground(Color.WHITE);
        lbl.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        topPanel.add(lbl);

        rollField = new JTextField(10);
        rollField.setBackground(new Color(45, 45, 45));
        rollField.setForeground(Color.WHITE);
        rollField.setCaretColor(Color.WHITE);
        rollField.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        topPanel.add(rollField);


        JButton searchBtn = new JButton("Search");
        searchBtn.setBackground( Color.WHITE);
        searchBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        searchBtn.setFocusPainted(false);
        topPanel.add(searchBtn);
        searchBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                searchBtn.setBackground(new Color(100, 150, 255));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                searchBtn.setBackground( Color.WHITE);
            }
        });

        JButton backBtn = new JButton("Back");
        backBtn.setBackground( Color.WHITE);
        backBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        backBtn.setFocusPainted(false);
        topPanel.add(backBtn);
        backBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                backBtn.setBackground(new Color(100, 150, 255));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                backBtn.setBackground( Color.WHITE);
            }
        });

        add(topPanel, BorderLayout.AFTER_LAST_LINE);

        table = new JTable();
        table.setBackground(new Color(50,50,50));
        table.setForeground(Color.WHITE);
        table.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        table.setRowHeight(30);

        JTableHeader header = table.getTableHeader();
        header.setBackground(new Color(100, 150, 255));
        header.setForeground(Color.WHITE);
        header.setFont(new Font("Times New Roman", Font.BOLD, 14));
        header.setReorderingAllowed(false);


        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.getViewport().setBackground(new Color(50,50,50));
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(scrollPane, BorderLayout.CENTER);

        searchBtn.addActionListener(e -> loadReport());
        backBtn.addActionListener(e -> {
            new MainMenu().setVisible(true);
            dispose();
        });
    }

    private void loadReport() {
        try {
            String rollText = rollField.getText().trim();
            if (rollText.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter Roll Number!", "Input Error", JOptionPane.WARNING_MESSAGE);
                return;
            }


            int roll = Integer.parseInt(rollField.getText().trim());

            String sql = "SELECT subject_name, marks FROM marks WHERE roll_no = ?";
            String sql2 = """
                SELECT s.name, s.student_class , AVG(m.marks) AS percentage, SUM(m.marks) AS total FROM students s JOIN marks m ON s.roll_no = m.roll_no WHERE s.roll_no = ? GROUP BY s.roll_no;""";

            DefaultTableModel model = new DefaultTableModel();
            model.addColumn("Subject");
            model.addColumn("Marks");

            ResultSet rs;

            try (Connection con = DBConnection.getConnection();
                 PreparedStatement ps = con.prepareStatement(sql)) {

                ps.setInt(1, roll);
                rs = ps.executeQuery();

                while (rs.next()) {
                    model.addRow(new Object[]{
                            rs.getString("subject_name"),
                            rs.getInt("marks")
                    });
                }
            }

            table.setModel(model);

            try (Connection con = DBConnection.getConnection();
                 PreparedStatement ps2 = con.prepareStatement(sql2)) {

                ps2.setInt(1, roll);
                rs = ps2.executeQuery();

                if (rs.next()) {
                    String name = rs.getString("name");
                    String cls = rs.getString("student_class");
                    double percentage = rs.getDouble("percentage");
                    int total = rs.getInt("total");
                    String grade = getGrade(percentage);

                    JOptionPane.showMessageDialog(this, "Name: " + name + "\nClass: " + cls + "\nTotal: " + total + "\nPercentage: " + percentage + "\nGrade: " + grade, "Student Report", JOptionPane.INFORMATION_MESSAGE);

                } else {
                    JOptionPane.showMessageDialog(this, "Student not found!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Invalid Roll Number!");
        }
    }

    private String getGrade(double per) {
        if (per >= 90) return "A+";
        if (per >= 80) return "A";
        if (per >= 70) return "B+";
        if (per >= 60) return "B";
        if (per >= 50) return "C";
        return "F";
    }
}
