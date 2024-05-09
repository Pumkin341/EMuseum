package emuseum;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import java.sql.*;


public class SellTickets extends JFrame {

    public SellTickets() {
        setTitle("Sell Tickets");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    
          
        database db = database.getInstance();
        db.view();

    }
    
}
