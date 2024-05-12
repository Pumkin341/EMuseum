package emuseum;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class CheckoutFrame extends JFrame {

    database db = database.getInstance();
    private DefaultTableModel model;
    private float totalPrice;

    public CheckoutFrame(DefaultTableModel model, JTextField totalField) {
        setTitle("Print Ticket");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 200);
        setLocationRelativeTo(null);

        this.model = model;
        this.totalPrice = Float.parseFloat(totalField.getText());

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        add(panel);

        JLabel titleLabel = new JLabel("Checkout");
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        panel.add(titleLabel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new GridLayout(1, 2));
        panel.add(centerPanel, BorderLayout.CENTER);

        JButton checkoutBtn = new JButton("Cash");
        centerPanel.add(checkoutBtn);

        JButton checkoutBtn2 = new JButton("Credit Card");
        centerPanel.add(checkoutBtn2);

        JPanel southPanel = new JPanel();
        panel.add(southPanel, BorderLayout.SOUTH);

        JButton exitBtn = new JButton("Exit");
        southPanel.add(exitBtn);

        checkoutBtn.addActionListener(e -> checkout("cash"));
        checkoutBtn2.addActionListener(e -> checkout("card"));
        exitBtn.addActionListener(e -> dispose());

        setVisible(true);
    }

    public void checkout(String paymentMethod) {

        // insert into orders table
        try {
            int totalNumberOftickets = 0;
            for (int i = 0; i < model.getRowCount(); i++) {
                totalNumberOftickets += (int) model.getValueAt(i, 1);
            }

            String insertQueryOrders = "INSERT INTO orders (reservation, amount, total_price, payment_method) VALUES (0, "
                    + totalNumberOftickets + ", " + totalPrice + " , '" + paymentMethod + "')";
            db.getStatement().executeUpdate(insertQueryOrders);
        } catch (SQLException ex) {
            ex.printStackTrace();

        }

        // insert into tickets table
        try {
            int order_id = 0;
            String selectQuery = "SELECT MAX(order_id) FROM orders";
            ResultSet rs = db.getStatement().executeQuery(selectQuery);
            while (rs.next()) {
                order_id = rs.getInt(1);
            }

            for (int i = 0; i < model.getRowCount(); i++) {
                String ticket_type = (String) model.getValueAt(i, 0);
                int number_of_tickets = Integer.parseInt(model.getValueAt(i, 1).toString());
                float price = Float.parseFloat(model.getValueAt(i, 2).toString());
                String hours = model.getValueAt(i, 3).toString();
                String reduction = (String) model.getValueAt(i, 4);
                String taxes = (String) model.getValueAt(i, 5);
                float total = Float.parseFloat(model.getValueAt(i, 6).toString());

                String insertQueryTickets = "INSERT INTO tickets (order_id, ticket_type, number_of_tickets, price, hours, reductions, taxes, total) VALUES ("
                        + order_id + ", '" + ticket_type + "', " + number_of_tickets + ", " + price + ", '" + hours
                        + "', '" + reduction + "', '" + taxes + "', " + total + ")";
                db.getStatement().executeUpdate(insertQueryTickets);
            }

            JOptionPane.showMessageDialog(null, "Checkout successful. Printing ticket...");

            // print ticket
            
            dispose();


        } catch (SQLException ex) {
            ex.printStackTrace();

        }
    }
}
