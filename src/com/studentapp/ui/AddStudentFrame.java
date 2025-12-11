package com.studentapp.ui;

import com.studentapp.dao.StudentOperations;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class AddStudentFrame extends JFrame {

    private JTextField rollField, nameField, classField;
    private JPanel subjectPanel;
    private final List<JTextField> subjectNames = new ArrayList<>();
    private final List<JTextField> subjectMarks = new ArrayList<>();

    public AddStudentFrame() {
        setTitle("Add Student");
        setSize(600, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(new Color(25, 25, 25));

        ImageIcon icon = new ImageIcon("src/com/studentapp/ui/Iconn.png");
        setIconImage(icon.getImage());

        JPanel titleBar = new JPanel();
        titleBar.setOpaque(true);
        titleBar.setBackground(new Color(10, 10, 10));
        titleBar.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        JLabel title = new JLabel("ADD STUDENT");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Times New Roman", Font.BOLD, 22));
        titleBar.add(title);

        add(titleBar, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridLayout(3, 2, 5, 5));
        formPanel.setBackground(new Color(25, 25, 25));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        formPanel.add(createLabel("Roll Number:"));
        rollField = createTextField();
        formPanel.add(rollField);

        formPanel.add(createLabel("Name:"));
        nameField = createTextField();
        formPanel.add(nameField);

        formPanel.add(createLabel("Class:"));
        classField = createTextField();
        formPanel.add(classField);

        rollField.addActionListener(e -> nameField.requestFocusInWindow());

        nameField.addActionListener(e -> classField.requestFocusInWindow());

        classField.addActionListener(e -> {
            if (subjectNames.isEmpty()) {
                addSubjectFields();
            }
            subjectNames.get(0).requestFocusInWindow();
        });


        add(formPanel, BorderLayout.BEFORE_FIRST_LINE);


        subjectPanel = new JPanel();
        subjectPanel.setLayout(new BoxLayout(subjectPanel, BoxLayout.Y_AXIS));
        subjectPanel.setBackground(new Color(25, 25, 25));

        JScrollPane scroll = new JScrollPane(subjectPanel);
        scroll.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        scroll.getViewport().setBackground(new Color(25, 25, 25));
        scroll.setPreferredSize(new Dimension(560, 250));
        scroll.setMaximumSize(new Dimension(560, 250));
        scroll.setAlignmentX(Component.CENTER_ALIGNMENT);

        add(scroll, BorderLayout.CENTER);


        JButton addSubjectBtn = createButton("Add Subject");
        JButton saveBtn = createButton("Save");
        JButton backBtn = createButton("Back");

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        btnPanel.setOpaque(true);
        btnPanel.setBackground(new Color(25, 25, 25));


        btnPanel.add(addSubjectBtn);
        btnPanel.add(saveBtn);
        btnPanel.add(backBtn);

        add(btnPanel, BorderLayout.SOUTH);


        addSubjectBtn.addActionListener(e -> addSubjectFields());
        saveBtn.addActionListener(e -> onSave());
        backBtn.addActionListener(e -> {
            new MainMenu().setVisible(true);
            dispose();
        });
    }


    private void addSubjectFields() {
        JPanel row = new JPanel(new GridLayout(1, 2, 5, 5));
        row.setBackground(new Color(25, 25, 25));
        row.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        JTextField subNameField =createTextField();
        JTextField subMarksField = createTextField();

        subNameField.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(90, 90, 90)), "SUBJECTS", 0, 0, new Font("TIMES NEW ROMAN", Font.PLAIN, 15), Color.WHITE));
        subMarksField.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(90, 90, 90)), "MARKS", 0, 0, new Font("TIMES NEW ROMAN", Font.PLAIN, 15), Color.WHITE));

        row.add(subNameField);
        row.add(subMarksField);

        subjectNames.add(subNameField);
        subjectMarks.add(subMarksField);

        subjectPanel.add(row);
        subjectPanel.revalidate();
        subjectPanel.repaint();

        subNameField.addActionListener(e -> subMarksField.requestFocusInWindow());
        subMarksField.addActionListener(e -> addSubjectFields());
        subNameField.requestFocusInWindow();
    }

    private JLabel createLabel(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setForeground(Color.WHITE);
        lbl.setFont(new Font("TIMES NEW ROMAN", Font.BOLD, 14));
        return lbl;
    }

    private JTextField createTextField() {
        JTextField tf = new JTextField();
        tf.setBackground(new Color(45, 45, 45));
        tf.setForeground(Color.WHITE);
        tf.setFont(new Font("TIMES NEW ROMAN", Font.BOLD, 14));
        tf.setCaretColor(Color.WHITE);
        tf.setBorder(BorderFactory.createLineBorder(new Color(100, 100, 100)));
        return tf;
    }

    private JButton createButton(String text) {
        JButton btn = new JButton(text);
        btn.setBackground(new Color(60, 120, 240));
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setPreferredSize(new Dimension(140, 35));
        btn.setFont(new Font("TIMES NEW ROMAN", Font.BOLD, 14));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btn.addMouseListener(new MouseAdapter() {

            public void mouseEntered(MouseEvent e) {
                btn.setBackground(new Color(30, 90, 200));
            }


            public void mouseExited(MouseEvent e) {
                btn.setBackground(new Color(60, 120, 240));
            }
        });

        return btn;
    }



    private void onSave() {
        try {
            String rollText = rollField.getText().trim();
            String name = nameField.getText().trim();
            String cls = classField.getText().trim();

            if (rollText.isEmpty() || name.isEmpty() || cls.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill Roll, Name and Class", "Validation Error", JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (!rollText.matches("\\d+")) {
                JOptionPane.showMessageDialog(this, "Roll number must be digits only.", "Validation Error", JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (subjectNames.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please add at least one subject", "Validation Error", JOptionPane.WARNING_MESSAGE);
                return;
            }

            int roll = Integer.parseInt(rollText);

            StudentOperations ops = new StudentOperations();
            boolean studentSaved = ops.addStudent(roll, name, cls);

            if (!studentSaved) {
                JOptionPane.showMessageDialog(this, "Could not save student (maybe roll number already exists).", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }


            for (int i = 0; i < subjectNames.size(); i++) {
                String sname = subjectNames.get(i).getText().trim();
                String smarks = subjectMarks.get(i).getText().trim();


                if (sname.isEmpty() && smarks.isEmpty()) continue;

                if (sname.isEmpty() || smarks.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Please fill both subject and marks for row " + (i + 1), "Validation Error", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                if (!smarks.matches("\\d+")) {
                    JOptionPane.showMessageDialog(this, "Marks must be a valid number in row " + (i + 1), "Validation Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                int marks = Integer.parseInt(smarks);
                if (marks < 0 || marks > 100) {
                    JOptionPane.showMessageDialog(this, "Marks must be between 0 – 100 in row " + (i + 1), "Validation Error", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                ops.addMarks(roll, sname, marks);
            }

            JOptionPane.showMessageDialog(this, "Student Added Successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);

            new MainMenu().setVisible(true);
            dispose();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Roll and Marks must be numbers.", "Number Format Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Unexpected Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
