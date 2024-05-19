package emuseum;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;

public class AddTicketTypeFrame extends JFrame {

    JTextField nameField = new JTextField(10);
    JTextField priceField = new JTextField(10);
    JTextField descriptionField = new JTextField(10);

    String headers1[] = {"Ticket ID", "Name", "Price", "Description"};  
    JTable table = new JTable(new DefaultTableModel(new Object[][]{}, headers1));

    database db = database.getInstance();

    public AddTicketTypeFrame() {

        setTitle("Sell Tickets");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1078, 600);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(1,2));
        add(panel);

        JPanel LeftPanel = new JPanel();
        LeftPanel.setLayout(new BoxLayout(LeftPanel, BoxLayout.Y_AXIS));
        panel.add(LeftPanel);

        JLabel nameLabel = new JLabel("Name");
        LeftPanel.add(nameLabel);
        LeftPanel.add(nameField);

        JLabel priceLabel = new JLabel("Price"); 
        LeftPanel.add(priceLabel);
        LeftPanel.add(priceField);

        JLabel descriptionLabel = new JLabel("Description");
        LeftPanel.add(descriptionLabel);
        LeftPanel.add(descriptionField);

        JButton addBtn = new JButton("Add");
        LeftPanel.add(addBtn);


        addBtn.addActionListener(e -> {
            addTicketType();
        });

        JPanel removePanel = new JPanel();
        panel.add(removePanel);

        JScrollPane scrollPane = new JScrollPane(table);
        removePanel.add(scrollPane);

        loadTable();

        JButton removeBtn = new JButton("Remove");
        removePanel.add(removeBtn);

        removeBtn.addActionListener(e -> {
            removeTicketType();
        });

        JButton exitBtn = new JButton("Exit");
        removePanel.add(exitBtn, BorderLayout.SOUTH);

        exitBtn.addActionListener(e -> {
            dispose();
        });
    }

    public void removeTicketType() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(null, "Please select a ticket type");
            return;
        }

        int id = Integer.parseInt(table.getValueAt(row, 0).toString());
        String query = "DELETE FROM ticket_types WHERE ticket_type_id = " + id;
        try {
            db.getStatement().executeUpdate(query);
            loadTable();

        } catch (SQLException ex) {
            System.out.println("Problem To Remove Data");
        }

        JOptionPane.showMessageDialog(null, "Ticket type removed successfully");
    }


    public void loadTable(){
        try {
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            model.setRowCount(0);

            String selectQuery = "SELECT * FROM ticket_types";
            var rs = db.getStatement().executeQuery(selectQuery);

            while (rs.next()) {
                model.addRow(new Object[]{rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4)});
            }

        } catch (Exception ex) {
            ex.printStackTrace();
    }
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
        loadTable();
    }

}
