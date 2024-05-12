package emuseum;

import java.awt.*;
import java.sql.*;
import javax.swing.*;

public class EditItemFrame extends JFrame {

    database db = database.getInstance();

    JTextField typeField = new JTextField(
            GUIButtons.galleryFrame.table.getValueAt(GUIButtons.galleryFrame.table.getSelectedRow(), 1).toString());
    JTextField descriptionField = new JTextField(
            GUIButtons.galleryFrame.table.getValueAt(GUIButtons.galleryFrame.table.getSelectedRow(), 2).toString());
    JTextField regionField = new JTextField(
            GUIButtons.galleryFrame.table.getValueAt(GUIButtons.galleryFrame.table.getSelectedRow(), 3).toString());
    JTextField yearField = new JTextField(
            GUIButtons.galleryFrame.table.getValueAt(GUIButtons.galleryFrame.table.getSelectedRow(), 4).toString());
    JTextField roomField = new JTextField(
            GUIButtons.galleryFrame.table.getValueAt(GUIButtons.galleryFrame.table.getSelectedRow(), 5).toString());

    public EditItemFrame() {
        setTitle("Edit Item");
        setSize(500, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new BorderLayout());
        add(panel);

        JLabel titleLabel = new JLabel("Edit Item");
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

        JButton saveBtn = new JButton("Edit");
        southPanel.add(saveBtn);
        saveBtn.addActionListener(e -> editItem());

        JButton cancelBtn = new JButton("Cancel");
        southPanel.add(cancelBtn);
        cancelBtn.addActionListener(e -> {
            dispose();
        });

        setVisible(true);
    }

    public void editItem() {
        try {

            String type = typeField.getText();
            String description = descriptionField.getText();
            String region = regionField.getText();
            int year = Integer.parseInt(yearField.getText());
            int room = Integer.parseInt(roomField.getText());
            int id = Integer.parseInt(
                    GUIButtons.galleryFrame.table.getValueAt(GUIButtons.galleryFrame.table.getSelectedRow(), 0).toString());

            String query = "UPDATE items SET type = '" + type + "', description = '" + description + "', region = '" + region
                    + "', year = '" + year + "', room = '" + room + "' WHERE id = " + id;
            db.getStatement().executeUpdate(query);

            GUIButtons.galleryFrame.loadTable();
            dispose();

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

}
