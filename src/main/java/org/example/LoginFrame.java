package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class LoginFrame extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private UserService userService;
    private JLabel loadingLabel;
    private JCheckBox rememberMeCheckBox;

    public LoginFrame() {
        setTitle("Login Page");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());

        // Create a gradient background panel
        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                GradientPaint gradient = new GradientPaint(0, 0, new Color(0, 123, 255), 0, getHeight(), new Color(0, 153, 255));
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        backgroundPanel.setLayout(new GridBagLayout());

        // Create components
        JLabel usernameLabel = new JLabel("Username:");
        usernameField = new JTextField(20);
        usernameField.setToolTipText("Enter your username");
        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField(20);
        passwordField.setToolTipText("Enter your password");
        passwordField.setEchoChar('*');

        // Show/Hide Password Button
        JButton togglePasswordButton = new JButton("Show");
        togglePasswordButton.addActionListener(e -> {
            if (passwordField.getEchoChar() == '*') {
                passwordField.setEchoChar((char) 0); // Show password
                togglePasswordButton.setText("Hide");
            } else {
                passwordField.setEchoChar('*'); // Hide password
                togglePasswordButton.setText("Show");
            }
        });

        JButton loginButton = new JButton("Login");
        JButton registerButton = new JButton("Register");

        // Add action listeners
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                loadingLabel.setVisible(true); // Show loading indicator
                User user = userService.loginUser(username, password);
                loadingLabel.setVisible(false); // Hide loading indicator
                if (user != null) {
                    JOptionPane.showMessageDialog(null, "Login successful! Welcome, " + user.getUsername() + "!");
                    redirectToMenuPage(); // Open the menu page
                    dispose(); // Close the login frame
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid username or password.", "Login Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Open registration dialog
                new RegistrationDialog(userService).setVisible(true);
            }
        });

        // Layout components
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;
        backgroundPanel.add(usernameLabel, gbc);
        gbc.gridx = 1;
        backgroundPanel.add(usernameField, gbc);
        gbc.gridx = 0;
        gbc.gridy = 1;
        backgroundPanel.add(passwordLabel, gbc);
        gbc.gridx = 1;
        backgroundPanel.add(passwordField, gbc);
        gbc.gridx = 2;
        backgroundPanel.add(togglePasswordButton, gbc);
        
        // Remember Me Checkbox
        rememberMeCheckBox = new JCheckBox("Remember Me");
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2; // Span across two columns
        backgroundPanel.add(rememberMeCheckBox, gbc);

        // Login Button
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 1; // Reset to default
        backgroundPanel.add(loginButton, gbc);

        // Register Button
        gbc.gridx = 1;
        backgroundPanel.add(registerButton, gbc);

        // Loading label
        loadingLabel = new JLabel("Logging in...");
        loadingLabel.setForeground(Color.RED);
        loadingLabel.setVisible(false);
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2; // Span across two columns
        backgroundPanel.add(loadingLabel, gbc);

        // Add background panel to frame
        add(backgroundPanel);

        // Database connection
        Connection connection = DatabaseConnection.getConnection();
        userService = new UserService(connection);
    }

    private void addHoverEffect(JButton button) {
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(new Color(0, 150, 255)); // Darker shade on hover
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(new Color(0, 123, 255)); // Original color
            }
        });
    }

    private void loadSavedCredentials() {
        // Load username and password from secure storage (e.g., encrypted file or database)
        // If credentials exist, set them in the usernameField and passwordField
    }

    private void redirectToMenuPage() {
        new MenuPage(); // Open the menu page
        dispose(); // Close the login frame
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginFrame().setVisible(true));
    }
}
