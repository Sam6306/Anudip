package com.studentapp.ui;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class MainMenu extends JFrame {

    public MainMenu() {
        setTitle("Student Performance Tracker");
        setBounds(0, 0, 500, 500);
        setUndecorated(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(50, 50, 50));

        ImageIcon icon = new ImageIcon("src/com/studentapp/ui/Iconn.png");
        setIconImage(icon.getImage());


        JPanel titleBar = new JPanel();
        titleBar.setOpaque(true);
        titleBar.setBackground(new Color(10, 10, 10));

        JLabel title = new JLabel("STUDENT PERFORMANCE TRACKER");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Times New Roman", Font.BOLD, 22));
        titleBar.add(title);

        add(titleBar, BorderLayout.NORTH);

        JPanel mainPanel = new JPanel();
        mainPanel.setOpaque(true);
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(new Color(30,30,30));
        mainPanel.setBorder(BorderFactory.createLineBorder(Color.magenta, 3));


        JLabel imgLabel = new JLabel(new ImageIcon());
        URL url = getClass().getResource("/com/studentapp/ui/Iconn.png");
        if (url!= null) {
            //imgLabel.setIcon(new ImageIcon(url));
            ImageIcon rawIcon = new ImageIcon(url);
            Image scaledImg = rawIcon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
            ImageIcon scaledIcon = new ImageIcon(scaledImg);
            imgLabel.setIcon(scaledIcon);
        }
        imgLabel.setHorizontalAlignment(SwingConstants.CENTER);
        imgLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0)); // spacing

        mainPanel.add(imgLabel, BorderLayout.NORTH);


        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        buttonPanel.setOpaque(true);
        buttonPanel.setBackground(new Color(30,30,30));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(40, 0, 0, 0));

        Color btnColor = new Color(60, 120, 240);
        Dimension btnSize = new Dimension(180, 35);

        JButton addBtn = createStyledButton("Add Student", btnColor, btnSize);
        JButton viewBtn = createStyledButton("View All Students", btnColor, btnSize);
        JButton reportBtn = createStyledButton("View Student Report", btnColor, btnSize);
        JButton deleteBtn = createStyledButton("Delete Student", btnColor, btnSize);
        JButton exitBtn = createStyledButton("Exit", btnColor, btnSize);

        addBtn.setFont(new Font("Times New Roman", Font.BOLD, 18));
        viewBtn.setFont(new Font("Times New Roman", Font.BOLD, 18));
        reportBtn.setFont(new Font("Times New Roman", Font.BOLD, 18));
        deleteBtn.setFont(new Font("Times New Roman", Font.BOLD, 18));
        exitBtn.setFont(new Font("Times New Roman", Font.BOLD, 18));

        buttonPanel.add(addBtn);
        buttonPanel.add(viewBtn);
        buttonPanel.add(reportBtn);
        buttonPanel.add(deleteBtn);
        buttonPanel.add(exitBtn);

        mainPanel.add(buttonPanel, BorderLayout.CENTER);
        add(mainPanel, BorderLayout.CENTER);

        setVisible(true);

        addBtn.addActionListener(e -> {
            new AddStudentFrame().setVisible(true);
            dispose();
        });
        viewBtn.addActionListener(e -> {
            new ViewStudentsFrame().setVisible(true);
            dispose();
        });
        reportBtn.addActionListener(e -> {
            new ViewReportFrame().setVisible(true);
            dispose();
        });
        deleteBtn.addActionListener(e -> {
            new DeleteStudentFrame().setVisible(true);
            dispose();
        });


        exitBtn.addActionListener(e -> System.exit(0));
    }

    public static void main(String[] args) {
        new MainMenu().setVisible(true);
    }

    private JButton createStyledButton(String text, Color bg, Dimension size) {
        JButton btn = new JButton(text);
        btn.setFocusPainted(false);
        btn.setForeground(Color.WHITE);
        btn.setBackground(bg);
        btn.setPreferredSize(size);
        btn.setFont(new Font("Arial", Font.PLAIN, 14));
        btn.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));


        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent e) {
                btn.setBackground(bg.darker());
            }
            public void mouseExited(java.awt.event.MouseEvent e) {
                btn.setBackground(bg);
            }
        });
        return btn;
    }
}
