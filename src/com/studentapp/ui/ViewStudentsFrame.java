package com.studentapp.ui;

import com.studentapp.db.DBConnection;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.sql.*;

public class ViewStudentsFrame extends JFrame {

    private JTable table;
    private DefaultTableModel model;

    public ViewStudentsFrame() {
        setTitle("All Students");
        setSize(700, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(new Color(50, 50, 50));

        ImageIcon icon = new ImageIcon("src/com/studentapp/ui/Iconn.png");
        setIconImage(icon.getImage());


        JPanel titleBar = new JPanel();
        titleBar.setOpaque(true);
        titleBar.setBackground(new Color(10, 10, 10));

        JLabel title = new JLabel("ALL STUDENTS DETAILS");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Times New Roman", Font.BOLD, 22));
        titleBar.add(title);

        add(titleBar, BorderLayout.NORTH);

        model = new DefaultTableModel();
        table = new JTable(model);

        table.setBackground(new Color(50, 50, 50));
        table.setForeground(Color.WHITE);
        table.setFont(new Font("Times New Roman", Font.BOLD, 15));
        table.setRowHeight(30);

        JTableHeader header = table.getTableHeader();
        header.setBackground(new Color(100, 150, 255));
        header.setForeground(Color.WHITE);
        header.setFont(new Font("Times New Roman", Font.BOLD, 20));
        header.setReorderingAllowed(false);

        JScrollPane sp = new JScrollPane(table);
        sp.getViewport().setBackground(new Color(25, 25, 25));
        sp.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));


        model.addColumn("Roll No");
        model.addColumn("Name");
        model.addColumn("Class");
        model.addColumn("Total Marks");
        model.addColumn("Percentage");
        model.addColumn("Grade");

        loadData();

        add(sp, BorderLayout.CENTER);

        JButton backBtn = new JButton("Back");
        backBtn.setPreferredSize(new Dimension(120, 35));
        backBtn.setBackground( Color.WHITE);
        backBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        backBtn.setFocusPainted(false);
        backBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                backBtn.setBackground(new Color(100, 150, 255));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                backBtn.setBackground( Color.WHITE);
            }
        });

        JPanel btnPanel = new JPanel(new FlowLayout());
        btnPanel.setBackground(new Color(50,50,50));
        btnPanel.add(backBtn);


        backBtn.addActionListener(e -> {
            new MainMenu().setVisible(true);
            dispose();
        });

        add(btnPanel, BorderLayout.SOUTH);
    }

    private void loadData() {
        String sql = """ 
                SELECT s.roll_no, s.name, s.student_class, SUM(m.marks) AS total,AVG(m.marks) AS avg_marks FROM students s JOIN marks m ON s.roll_no = m.roll_no GROUP BY s.roll_no; """;

        try (Connection con = DBConnection.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                int roll = rs.getInt("roll_no");
                String name = rs.getString("name");
                String cls = rs.getString("student_class");
                int total = rs.getInt("total");
                float percentage = rs.getFloat("avg_marks");
                String grade = getGrade(percentage);

                model.addRow(new Object[]{roll, name, cls, total, percentage, grade});
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
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
