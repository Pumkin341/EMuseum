package emuseum;

import java.awt.*;
import javax.swing.*;

public class AddItemFrame extends JFrame {

    database db = database.getInstance();

    JTextField typeField = new JTextField();
    JTextField descriptionField = new JTextField();
    JTextField regionField = new JTextField();
    JTextField yearField = new JTextField();
    JTextField roomField = new JTextField();

    public AddItemFrame() {
        setTitle("Add Item");
        setSize(500, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new BorderLayout());
        add(panel);

        JLabel titleLabel = new JLabel("Add Item");
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        panel.add(titleLabel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new GridLayout(6, 2));
        panel.add(centerPanel, BorderLayout.CENTER);

        JLabel typeLabel = new JLabel("Type");
        centerPanel.add(typeLabel);
        centerPanel.add(typeField);

        JLabel descriptionLabel = new JLabel("Description");
        centerPanel.add(descriptionLabel);
        centerPanel.add(descriptionField);

        JLabel regionLabel = new JLabel("Region");
        centerPanel.add(regionLabel);
        centerPanel.add(regionField);

        JLabel yearLabel = new JLabel("Year");
        centerPanel.add(yearLabel);
        centerPanel.add(yearField);

        JLabel roomLabel = new JLabel("Room");
        centerPanel.add(roomLabel);
        centerPanel.add(roomField);

        JPanel southPanel = new JPanel(new GridLayout(1, 2));
        panel.add(southPanel, BorderLayout.SOUTH);

        JButton saveBtn = new JButton("Save");
        southPanel.add(saveBtn);
        saveBtn.addActionListener(e -> addToDatabase());

        JButton cancelBtn = new JButton("Cancel");
        southPanel.add(cancelBtn);
        cancelBtn.addActionListener(e -> {
            dispose();
        });

        setVisible(true);
    }

    public void addToDatabase() {
        try {
            String type = typeField.getText();
            String description = descriptionField.getText();
            String region = regionField.getText();
            int year = Integer.parseInt(yearField.getText());
            int room = Integer.parseInt(roomField.getText());

            if (type.isEmpty() || description.isEmpty() || region.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please fill all fields");
                return;
            }

            String query = "INSERT INTO items (type, description, region, year, room) VALUES ('" + type + "', '" + description + "', '" + region + "', " + year + ", " + room + ")";
            db.getStatement().executeUpdate(query);

            JOptionPane.showMessageDialog(null, "Item added successfully");
           
            GUIButtons.galleryFrame.loadTable();
            dispose();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }

    }
}
