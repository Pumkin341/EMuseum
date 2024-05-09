package emuseum;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

class GUIButtons extends JFrame {

    public GUIButtons() {

        setTitle("eMuseum");
        setSize(400, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        setLayout(new GridLayout(5, 1));

        JButton btn1 = new JButton("Sell Ticket");
        JButton btn2 = new JButton("Reserve Ticket");
        JButton btn3 = new JButton("Gallery");
        JButton btn4 = new JButton("Summary");
        JButton btn5 = new JButton("Exit");

        add(btn1);
        add(btn2);
        add(btn3);
        add(btn4);
        add(btn5);

        btn1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new SellTickets();
            }
        });

        btn2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ReserveTicket();
            }
        });

        btn3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Gallery();
            }
        });

        btn4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Summary();
            }
        });

        btn5.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        setVisible(true);
    }
}

public class Emuseum {

    public static void main(String[] args) {
        new GUIButtons();

        database db = database.getInstance();
        db.view();
    }
}
