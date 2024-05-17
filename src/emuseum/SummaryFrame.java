package emuseum;

import javax.swing.*;
import java.awt.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;

public class SummaryFrame extends JFrame {

    database db = database.getInstance();

    String[] headers = { "Id", "Order Id", "Type", "Number of Tickets", "Price", "Hours", "Reductions", "Taxes", "Total"};
    JTable table = new JTable(new DefaultTableModel(new Object[][] {}, headers));

    public SummaryFrame() {
        setTitle("Summary");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new BorderLayout());
        add(panel);

        JLabel titleLabel = new JLabel("Summary");
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        panel.add(titleLabel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new GridLayout(2,1));
        panel.add(centerPanel, BorderLayout.CENTER);

        JScrollPane scrollPane = new JScrollPane(table);
        centerPanel.add(scrollPane);

        JPanel totalPanel = new JPanel(new GridLayout(1,2));
        centerPanel.add(totalPanel);

        JLabel totalLabel = new JLabel("Total: ");
        totalPanel.add(totalLabel);

        double total = 0;
        try {
            String query = "SELECT * FROM tickets";
            ResultSet rs = db.getStatement().executeQuery(query);
            
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            
            while(rs.next()){
                model.addRow(new Object[] { rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9)});
                total += rs.getDouble(9);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        JTextField totalField = new JTextField("$" + total);
        totalField.setEditable(false);
        totalPanel.add(totalField);

        JButton exitBtn = new JButton("Exit");
        panel.add(exitBtn, BorderLayout.SOUTH);
        exitBtn.addActionListener(e -> dispose());


    }

}
