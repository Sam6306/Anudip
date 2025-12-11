package com.studentapp.ui;

import com.studentapp.db.DBConnection;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class DeleteStudentFrame extends JFrame {

    private JTextField rollField;

    public DeleteStudentFrame() {
        setTitle("Delete Student");
        setSize(400, 200);
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

        JLabel title = new JLabel("DELETE STUDENT");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Times New Roman", Font.BOLD, 22));
        titleBar.add(title);

        add(titleBar, BorderLayout.NORTH);

        JPanel panel = new JPanel();
        panel.setOpaque(true);
        panel.setBackground(new Color(50,50,50));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel LBL =new JLabel("Enter Roll No to Delete: ");
        LBL.setForeground(Color.WHITE);
        LBL.setFont(new Font("TIMES NEW ROMAN", Font.BOLD, 16));
        panel.add(LBL);
        rollField = new JTextField(10);
        panel.add(rollField);
        add(panel,BorderLayout.CENTER);

        JButton deleteBtn = new JButton("Delete");
        deleteBtn.setBackground( Color.WHITE);
        deleteBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        deleteBtn.setFocusPainted(false);
        deleteBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                deleteBtn.setBackground(new Color(100, 150, 255));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                deleteBtn.setBackground( Color.WHITE);
            }
        });

        JButton backBtn = new JButton("Back");
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

        JPanel btnPanel = new JPanel();
        btnPanel.setOpaque(true);
        btnPanel.setBackground(new Color(50,50,50));
        btnPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        btnPanel.add(deleteBtn);
        btnPanel.add(backBtn);
        add(btnPanel,BorderLayout.SOUTH);

        deleteBtn.addActionListener(e -> deleteStudent());
        backBtn.addActionListener(e -> {
            new MainMenu().setVisible(true);
            dispose();
        });
    }

    private void deleteStudent() {
        try {
            String rollText = rollField.getText().trim();
            if (rollText.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter Roll Number!", "Input Error", JOptionPane.WARNING_MESSAGE);
                return;
            }

            int roll = Integer.parseInt(rollField.getText().trim());

            String sql = "DELETE FROM students WHERE roll_no = ?";

            try (Connection con = DBConnection.getConnection();
                 PreparedStatement ps = con.prepareStatement(sql)) {

                ps.setInt(1, roll);
                int rows = ps.executeUpdate();

                if (rows > 0) {
                    JOptionPane.showMessageDialog(this, "Student Deleted Successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "Student Not Found!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }

            new MainMenu().setVisible(true);
            dispose();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Invalid Roll Number!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
