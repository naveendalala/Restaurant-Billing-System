package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuPage extends JFrame {
    private DefaultListModel<String> orderListModel; // Model for the order list
    private JLabel totalCostLabel; // Label to display total cost
    private double totalCost = 0.0; // Variable to keep track of total cost

    public MenuPage() {
        setTitle("Menu Page");
        setSize(800, 600); // Increased size for better layout
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout()); // Use BorderLayout for main layout

        // Create a panel for menu items
        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new GridLayout(0, 2)); // Two columns for menu items
        menuPanel.setBackground(Color.LIGHT_GRAY); // Background color for menu panel

        // Expanded menu items with costs
        String[][] menuItems = {
            {"Pizza", "$10.00"},
            {"Burger", "$8.00"},
            {"Pasta", "$12.00"},
            {"Salad", "$7.00"},
            {"Sushi", "$15.00"},
            {"Tacos", "$9.00"},
            {"Steak", "$20.00"},
            {"Fries", "$4.00"},
            {"Ice Cream", "$5.00"},
            {"Cake", "$6.00"},
            {"Sandwich", "$7.50"},
            {"Wrap", "$8.50"}
        };

        for (String[] item : menuItems) {
            addMenuItem(menuPanel, item[0], item[1]); // Pass item name and cost
        }

        // Create a list to display ordered items
        orderListModel = new DefaultListModel<>();
        JList<String> orderList = new JList<>(orderListModel);
        orderList.setBorder(BorderFactory.createTitledBorder("Your Orders"));

        // Create a panel for total cost and print button
        JPanel totalPanel = new JPanel();
        totalCostLabel = new JLabel("Total Cost: $0.00");
        JButton printButton = new JButton("Print Order");

        printButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                printOrder();
            }
        });

        totalPanel.add(totalCostLabel);
        totalPanel.add(printButton);

        // Add components to the main frame
        add(menuPanel, BorderLayout.CENTER);
        add(new JScrollPane(orderList), BorderLayout.EAST); // Add order list to the right side
        add(totalPanel, BorderLayout.SOUTH); // Add total panel at the bottom

        setVisible(true);
    }

    private void addMenuItem(JPanel menuPanel, String itemName, String itemCost) {
        JPanel itemPanel = new JPanel();
        itemPanel.setLayout(new FlowLayout());

        JLabel itemLabel = new JLabel(itemName + " - " + itemCost); // Display name and cost
        itemLabel.setFont(new Font("Arial", Font.BOLD, 16)); // Set font for item label
        itemLabel.setForeground(Color.BLUE); // Set color for item label

        // Add mouse listener to the label
        itemLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                orderListModel.addElement(itemName + " for " + itemCost); // Add item to order list
                updateTotalCost(itemCost); // Update total cost
            }
        });

        itemPanel.add(itemLabel);
        itemPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY)); // Add border for better visibility
        menuPanel.add(itemPanel);
    }

    private void updateTotalCost(String itemCost) {
        // Parse the cost and update the total
        double cost = Double.parseDouble(itemCost.replace("$", ""));
        totalCost += cost;
        totalCostLabel.setText("Total Cost: $" + String.format("%.2f", totalCost)); // Update total cost label
    }

    private void printOrder() {
        StringBuilder orderSummary = new StringBuilder("Your Order Summary:\n\n");
        for (int i = 0; i < orderListModel.size(); i++) {
            orderSummary.append(orderListModel.get(i)).append("\n");
        }
        orderSummary.append("\nTotal Cost: $").append(String.format("%.2f", totalCost));

        // Create a JTextArea for better formatting
        JTextArea textArea = new JTextArea(orderSummary.toString());
        textArea.setEditable(false);
        textArea.setFont(new Font("Arial", Font.PLAIN, 14));
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Show the order summary in a dialog
        JOptionPane.showMessageDialog(this, new JScrollPane(textArea), "Order Summary", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MenuPage::new);
    }
}
