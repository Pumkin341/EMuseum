package emuseum;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ReserveTicketFrame extends JFrame {

    String[] headers = {"Name", "Phone Number", "Email", "Date", "Hours", "Number of Tickets"};
    JTable table = new JTable(new DefaultTableModel(headers, 0));
    JScrollPane scrollPane = new JScrollPane(table);

    JTextField nameField = new JTextField();
    JTextField phoneNumberField = new JTextField();
    JTextField emailField = new JTextField();
    JTextField dateField = new JTextField();
    JRadioButton hoursRadioButton1 = new JRadioButton("9-17");
    JRadioButton hoursRadioButton2 = new JRadioButton("17-20");
    JTextField numberField = new JTextField();

    database db = database.getInstance();

    public ReserveTicketFrame() {

        setTitle("Reservations");
        setSize(1200, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        setLayout(new GridLayout(1, 2));

        JPanel panelLeft = new JPanel(new GridLayout(7, 1));
        add(panelLeft);

        JPanel namePanel = new JPanel(new GridLayout(1, 2));
        JLabel nameLabel = new JLabel("Name: ");
        namePanel.add(nameLabel);
        namePanel.add(nameField);
        panelLeft.add(namePanel);

        JPanel phonePanel = new JPanel(new GridLayout(1, 2));
        JLabel phoneNumberLabel = new JLabel("Phone Number: ");
        phonePanel.add(phoneNumberLabel);
        phonePanel.add(phoneNumberField);
        panelLeft.add(phonePanel);

        JPanel emailPanel = new JPanel(new GridLayout(1, 2));
        JLabel emailLabel = new JLabel("Email: ");
        emailPanel.add(emailLabel);
        emailPanel.add(emailField);
        panelLeft.add(emailPanel);

        JPanel datePanel = new JPanel(new GridLayout(1, 2));
        JLabel dateLabel = new JLabel("Date: ");
        datePanel.add(dateLabel);
        datePanel.add(dateField);
        panelLeft.add(datePanel);

        JPanel hoursPanel = new JPanel(new GridLayout(1, 3));
        JLabel hoursLabel = new JLabel("hours: ");
        hoursPanel.add(hoursLabel);
        hoursPanel.add(hoursRadioButton1);
        hoursPanel.add(hoursRadioButton2);
        panelLeft.add(hoursPanel);

        JPanel numberPanel = new JPanel(new GridLayout(1, 2));
        JLabel numberLabel = new JLabel("Number of Tickets: ");
        numberPanel.add(numberLabel);
        numberPanel.add(numberField);
        panelLeft.add(numberPanel);

        JPanel btnsPanel = new JPanel(new GridLayout(1, 2));
        JButton reserveBtn = new JButton("Reserve");
        JButton exitBtn = new JButton("Exit");
        btnsPanel.add(reserveBtn);
        btnsPanel.add(exitBtn);
        panelLeft.add(btnsPanel);

        add(scrollPane);

        reserveBtn.addActionListener(e -> reserve());
        exitBtn.addActionListener(e -> dispose());

        loadTable();

        setVisible(true);
    }

    public void loadTable(){
        try {
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            model.setRowCount(0);

            String selectQuery = "SELECT * FROM reservations";
            var rs = db.getStatement().executeQuery(selectQuery);

            while (rs.next()) {
                String name = rs.getString("name");
                String phoneNumber = rs.getString("phone_number");
                String email = rs.getString("email");
                String date = rs.getString("date");
                String hours = rs.getString("hours");
                int numberOfTickets = rs.getInt("number_of_tickets");

                model.addRow(new Object[]{name, phoneNumber, email, date, hours, numberOfTickets});
            }

        } catch (Exception ex) {
            ex.printStackTrace();
    }
}
    public void reserve() {

        try {
            String name = nameField.getText();
            String phoneNumber = phoneNumberField.getText();
            String email = emailField.getText();
            String date = dateField.getText();
            String hours = hoursRadioButton1.isSelected() ? "9-17" : "17-20";
            int numberOfTickets = Integer.parseInt(numberField.getText());

            if(numberOfTickets >= 5){
                JOptionPane.showMessageDialog(this, "Number of tickets must be less than 5.");
                return;
            }

            String insertQuery = "INSERT INTO reservations (name, phone_number, email, date, hours, number_of_tickets) VALUES ('"
                    + name + "', '" + phoneNumber + "', '" + email + "', '" + date + "', '" + hours + "', " + numberOfTickets
                    + ")";

            db.getStatement().executeUpdate(insertQuery);
            loadTable();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
    }
}