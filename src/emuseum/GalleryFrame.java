package emuseum;

import java.awt.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

public class GalleryFrame extends JFrame{
    
    database db = database.getInstance();

    String[] headers = {"Id", "Type", "Description", "Region", "Year", "Room"};
    JTable table = new JTable(new DefaultTableModel(new Object[][]{}, headers));

    public GalleryFrame(int admin) {
        setTitle("Gallery");
        setSize(500, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new BorderLayout());
        add(panel);

        JLabel titleLabel = new JLabel("Gallery");
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        panel.add(titleLabel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel();
        panel.add(centerPanel, BorderLayout.CENTER);

        JScrollPane scrollPane = new JScrollPane(table);
        centerPanel.add(scrollPane);

        JPanel southPanel = new JPanel(new GridLayout(1,4));
        panel.add(southPanel, BorderLayout.SOUTH);

        loadTable();

        table.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                loadTable();
            }
        });

        if (admin == 1) {
            JButton addBtn = new JButton("Add");
            southPanel.add(addBtn);
            addBtn.addActionListener(e -> new AddItemFrame());

            JButton editBtn = new JButton("Edit");
            southPanel.add(editBtn);
            editBtn.addActionListener(e -> editItem());

            JButton deleteBtn = new JButton("Delete");
            southPanel.add(deleteBtn);
            deleteBtn.addActionListener(e -> deleteItem());
        }

        JButton exitBtn = new JButton("Exit");
        southPanel.add(exitBtn);

        exitBtn.addActionListener(e -> {
            dispose();
        });
        
        setVisible(true);
    }

    public TableModel getTableModel(){
        return table.getModel();
    }

    public void editItem() {
        if (table.getSelectedRow() == -1) {
            JOptionPane.showMessageDialog(null, "Please select a row");
            return;
        }
        new EditItemFrame();
    }
    public void loadTable(){
        try {
            String query = "select * from items";
            ResultSet result = db.getStatement().executeQuery(query);

            DefaultTableModel model = (DefaultTableModel) table.getModel();
            model.setRowCount(0);

            while (result.next()) {
                model.addRow(new Object[]{result.getString(1), result.getString(2), result.getString(3), result.getString(4), result.getString(5), result.getString(6)});
            }
        } catch (SQLException ex) {
            System.out.println("Problem To Show Data");
        }
    }

    public void deleteItem(){
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(null, "Please select a row");
            return;
        }

        int id = Integer.parseInt(table.getValueAt(row, 0).toString());
        try {
            String query = "delete from items where id = " + id;
            db.getStatement().executeUpdate(query);
            loadTable();
        } catch (SQLException ex) {
            System.out.println("Problem To Delete Data");
        }

    }

}
