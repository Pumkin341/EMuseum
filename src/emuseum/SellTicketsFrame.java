
package emuseum;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class SellTicketsFrame extends JFrame {

    private JPanel panel = new JPanel();
    private JLabel titleLabel = new JLabel("Ticket Sales");
    private JButton printBtn = new JButton("Pay");
    private JButton exitBtn = new JButton("Exit");
    private JPanel southPanel = new JPanel();
    private JButton calculateBtn = new JButton("Calculate Price");
    private JTextField totalField = new JTextField(10);
    private JPanel centerPanel = new JPanel(new GridLayout(7, 1));
    private JPanel numberofticketsPanel = new JPanel(new GridLayout(1, 2));
    private JLabel numberofticketsLabel = new JLabel("Number of Tickets");
    private JTextField numberofticketsField = new JTextField(10);
    private JPanel hoursPanel = new JPanel(new GridLayout(3, 1));
    private JLabel hoursLabel = new JLabel("Hours");
    private JRadioButton hours1 = new JRadioButton("9-17");
    private JRadioButton hours2 = new JRadioButton("17-20");
    private JPanel reductionPanel = new JPanel(new GridLayout(4, 1));
    private JLabel reductionLabel = new JLabel("Reductions");
    private JRadioButton reduction1 = new JRadioButton("Student");
    private JRadioButton reduction2 = new JRadioButton("Soldier");
    private JRadioButton reduction3 = new JRadioButton("Retired");
    private JPanel taxPanel = new JPanel(new GridLayout(3, 1));
    private JLabel taxLabel = new JLabel("Taxes");
    private JRadioButton tax1 = new JRadioButton("Photos");
    private JRadioButton tax2 = new JRadioButton("Film");
    JPanel checkoutPanel = new JPanel(new GridLayout(1, 2));
    JLabel checkoutLabel = new JLabel("Checkout");
    JButton checkoutBtn = new JButton("Add to Cart");
    
    String headers1[] = {"Ticket ID", "Name", "Price", "Description"};  
    JTable tableTicketTypes = new JTable(new DefaultTableModel(new Object[][]{}, headers1));
    String headers2[] = {"Type", "#", "Price", "Hours", "Reductions", "Taxes", "Total"};
    JTable tableTickets = new JTable(new DefaultTableModel(new Object[][]{}, headers2));

    database db = database.getInstance();
    
    public SellTicketsFrame() {
        
        setTitle("Sell Tickets");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 800);
        setLocationRelativeTo(null);

   
        panel.setLayout(new BorderLayout());
        add(panel);

        // title north
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        panel.add(titleLabel, BorderLayout.NORTH);
        //print button east
        panel.add(printBtn, BorderLayout.EAST);
        //exit button west
        panel.add(exitBtn, BorderLayout.WEST);
      
        // south panel
        southPanel.add(calculateBtn);
        totalField.setEditable(false);
        southPanel.add(totalField);
        panel.add(southPanel, BorderLayout.SOUTH);

        
        // center panel
        panel.add(centerPanel, BorderLayout.CENTER);

        try {
            String insertquery = "select * from ticket_types";
            ResultSet result = db.getStatement().executeQuery(insertquery);
            
            DefaultTableModel model = (DefaultTableModel) tableTicketTypes.getModel();
            while (result.next()) {
                model.addRow(new Object[]{result.getString(1), result.getString(2), result.getString(3), result.getString(4)});
            }
            
        } catch (SQLException ex) {
            System.out.println("Problem To Show Data");
        }
        
        JScrollPane scrollPane = new JScrollPane(tableTicketTypes);
        centerPanel.add(scrollPane);

        // number of tickets panel
        numberofticketsPanel.add(numberofticketsLabel);
        numberofticketsPanel.add(numberofticketsField);
        centerPanel.add(numberofticketsPanel);

        //hours panel
        hoursPanel.add(hoursLabel);
        hoursPanel.add(hours1);
        hoursPanel.add(hours2);
        centerPanel.add(hoursPanel);

        // reduction panel
        reductionPanel.add(reductionLabel);
        reductionPanel.add(reduction1);
        reductionPanel.add(reduction2);
        reductionPanel.add(reduction3);
        centerPanel.add(reductionPanel);


        // tax panel
        taxPanel.add(taxLabel);
        taxPanel.add(tax1);
        taxPanel.add(tax2);
        centerPanel.add(taxPanel);

        // checkout panel
        JButton deleteBtn = new JButton("Delete Row");
        checkoutPanel.add(checkoutBtn);
        checkoutPanel.add(deleteBtn);
        centerPanel.add(checkoutPanel);
        
        //checkout table
        JScrollPane scrollPane2 = new JScrollPane(tableTickets);
        centerPanel.add(scrollPane2);
    
        //event listeners

        checkoutBtn.addActionListener(e -> addToCart());
        calculateBtn.addActionListener(e -> calculateTotal());
        exitBtn.addActionListener(e -> dispose());
        printBtn.addActionListener(e -> printTicket());
        deleteBtn.addActionListener(e -> deleteRow());

        // event listeners
        hours1.addActionListener(e -> {
            hours2.setSelected(false);
        });

        hours2.addActionListener(e -> {
            hours1.setSelected(false);
        });

        reduction1.addActionListener(e -> {
            reduction2.setSelected(false);
            reduction3.setSelected(false);
        });

        reduction2.addActionListener(e -> {
            reduction1.setSelected(false);
            reduction3.setSelected(false);
        });

        reduction3.addActionListener(e -> {
            reduction1.setSelected(false);
            reduction2.setSelected(false);
        });

        tax1.addActionListener(e -> {
            tax2.setSelected(false);
        });

        tax2.addActionListener(e -> {
            tax1.setSelected(false);
        });

        setVisible(true);
    }

    public void printTicket(){
        if (totalField.getText().equals(""))  {
            JOptionPane.showMessageDialog(this, "Total price is empty");
            return;
        }

        DefaultTableModel model = (DefaultTableModel) tableTickets.getModel();
        new CheckoutFrame(model, totalField);
        dispose();
    }


    public void calculateTotal() {
        DefaultTableModel model = (DefaultTableModel) tableTickets.getModel();
        double total = 0;
        int totalTickets = 0;
        for (int i = 0; i < model.getRowCount(); i++) {
            total += Double.parseDouble(model.getValueAt(i, 6).toString());
            totalTickets += Integer.parseInt(model.getValueAt(i, 1).toString());
        }

        if(totalTickets > 10) total = total - total * 0.2;
        totalField.setText(String.valueOf(total));
    }

    public void addToCart(){
        DefaultTableModel model1 = (DefaultTableModel) tableTicketTypes.getModel();
        DefaultTableModel model2 = (DefaultTableModel) tableTickets.getModel();

        int selectedRow = tableTicketTypes.getSelectedRow();
        if(selectedRow == -1) return;
        if(getNumberOfTickets() == 0) {
            JOptionPane.showMessageDialog(this, "Please enter a number of tickets");
            return;
        }
        model2.addRow(new Object[]{model1.getValueAt(selectedRow, 1) , getNumberOfTickets(), model1.getValueAt(selectedRow, 2), getHours(), getReductions(), getTaxes(), getTicketPrice()});
    }

    public double getTicketPrice() {
        double price = 0;
        DefaultTableModel model1 = (DefaultTableModel) tableTicketTypes.getModel();

        price += Double.parseDouble(model1.getValueAt(tableTicketTypes.getSelectedRow(), 2).toString());

        if(reduction1.isSelected() || reduction2.isSelected() || reduction3.isSelected()) price = price / 2 ;
        if(hours2.isSelected()) price += 5;
        if(tax1.isSelected()) price += 2;
        if(tax2.isSelected()) price += 3;

        price = price * Integer.parseInt(numberofticketsField.getText());
        return price;
    }

    public void deleteRow(){
        DefaultTableModel model = (DefaultTableModel) tableTickets.getModel();
        int selectedRow = tableTickets.getSelectedRow();
        if(selectedRow == -1) return;
        model.removeRow(selectedRow);
    }

    public int getNumberOfTickets() {
        if(numberofticketsField.getText().equals("") || numberofticketsField.getText().equals(" ")) return 0;
        if(Integer.parseInt(numberofticketsField.getText()) > 0) return Integer.parseInt(numberofticketsField.getText());
        return 0;
    }

    public String getHours() {
        if (hours1.isSelected()) return new String("9-17");
        if (hours2.isSelected()) return new String("17-20");
        return "";
    }

    public String getReductions() {
        if(reduction1.isSelected()) return "Student";
        if(reduction2.isSelected()) return "Soldier";
        if(reduction3.isSelected()) return "Retired";
        return "";
    }

    public String getTaxes() {
        if(tax1.isSelected()) return "Photos";
        if(tax2.isSelected()) return "Film";
        return "";
    }
}
