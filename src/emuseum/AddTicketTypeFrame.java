package emuseum;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.*;
import java.sql.*;

public class AddTicketTypeFrame extends JFrame {

    JTextField nameField = new JTextField(10);
    JTextField priceField = new JTextField(10);
    JTextField descriptionField = new JTextField(10);

    database db = database.getInstance();

    public AddTicketTypeFrame() {

        setTitle("Sell Tickets");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout());
        add(panel);

        JLabel titleLabel = new JLabel("Add Ticket Type");
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        panel.add(titleLabel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        panel.add(centerPanel, BorderLayout.CENTER);

        JLabel nameLabel = new JLabel("Name");
        centerPanel.add(nameLabel);
        centerPanel.add(nameField);

        JLabel priceLabel = new JLabel("Price"); 
        centerPanel.add(priceLabel);
        centerPanel.add(priceField);

        JLabel descriptionLabel = new JLabel("Description");
        centerPanel.add(descriptionLabel);
        centerPanel.add(descriptionField);

        JPanel southPanel = new JPanel(new GridLayout(1,2));
        panel.add(southPanel, BorderLayout.SOUTH);

        JButton addBtn = new JButton("Add");
        southPanel.add(addBtn, BorderLayout.SOUTH);


        addBtn.addActionListener(e -> {
            addTicketType();
        });

        JButton exitBtn = new JButton("Exit");
        southPanel.add(exitBtn, BorderLayout.SOUTH);

        exitBtn.addActionListener(e -> {
            dispose();
        });
    }

    public void addTicketType() {
        String name = nameField.getText();
        String price = priceField.getText();
        String description = descriptionField.getText();

        if( name.isEmpty() || price.isEmpty() || description.isEmpty() ) {
            JOptionPane.showMessageDialog(null, "All fields are required");
            return;
        }
        else {
            try {
                Double.parseDouble(price);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Price must be a number");
                return;
            }

            try {
                String insertquery = "select * from ticket_types where name = '" + name + "'";
                ResultSet result = db.getStatement().executeQuery(insertquery);
                if (result.next()) {
                    JOptionPane.showMessageDialog(null, "Ticket type already exists");
                    return;
                }
            } catch (SQLException ex) {
                System.out.println("Problem To Show Data");
            }

        }
        String query = "INSERT INTO ticket_types (name, price, description) VALUES ('" + name + "', '" + price + "', '"
                + description + "')";
        try {
            db.getStatement().executeUpdate(query);
        } catch (SQLException ex) {
            System.out.println("Problem To Add Data");
        }
        
        JOptionPane.showMessageDialog(null, "Ticket type added successfully");
    }

}
