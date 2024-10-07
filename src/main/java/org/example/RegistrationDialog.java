package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegistrationDialog extends JDialog {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JComboBox<String> roleComboBox;
    private UserService userService;

    public RegistrationDialog(UserService userService) {
        this.userService = userService;
        setTitle("Register");
        setSize(300, 250);
        setLayout(new GridBagLayout());
        setLocationRelativeTo(null);
        setModal(true); // Make the dialog modal

        // Create components
        JLabel usernameLabel = new JLabel("Username:");
        usernameField = new JTextField(20);
        usernameField.setToolTipText("Enter your username");
        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField(20);
        passwordField.setToolTipText("Enter your password");
        JLabel roleLabel = new JLabel("Role:");
        roleComboBox = new JComboBox<>(new String[]{"Admin", "Waiter", "Cashier"});
        JButton registerButton = new JButton("Register");

        // Add action listener for the register button
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                String role = (String) roleComboBox.getSelectedItem();
                if (userService.registerUser(username, password, role)) {
                    JOptionPane.showMessageDialog(null, "Registration successful!");
                    dispose(); // Close the registration dialog
                    new MenuPage().setVisible(true); // Open the menu page after successful registration
                } else {
                    JOptionPane.showMessageDialog(null, "Registration failed. Username may already exist.", "Registration Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Layout components with proper spacing
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Padding
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(usernameLabel, gbc);
        gbc.gridx = 1;
        add(usernameField, gbc);
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(passwordLabel, gbc);
        gbc.gridx = 1;
        add(passwordField, gbc);
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(roleLabel, gbc);
        gbc.gridx = 1;
        add(roleComboBox, gbc);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2; // Span across two columns
        add(registerButton, gbc);
    }
}
