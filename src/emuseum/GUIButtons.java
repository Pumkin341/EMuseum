package emuseum;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class GUIButtons extends JFrame {

    public static GalleryFrame galleryFrame;
    public static SellTicketsFrame sellTicketsFrame;
    public static AddTicketTypeFrame addTicketTypeFrame;
    public static ReserveTicketFrame reserveTicketFrame;
    public static SummaryFrame summaryFrame;
    public GUIButtons(int admin) {

        setTitle("eMuseum");
        setSize(400, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        setLayout(new GridLayout(6, 1));

        JButton btn1 = new JButton("Sell Tickets");
        JButton btn2 = new JButton("Add ticket type");
        JButton btn3 = new JButton("Reservations");
        JButton btn4 = new JButton("Gallery");
        JButton btn5 = new JButton("Summary");
        JButton btn6 = new JButton("Exit");

        add(btn1);
        add(btn2);
        add(btn3);
        add(btn4);
        add(btn5);
        add(btn6);

        btn1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sellTicketsFrame = new SellTicketsFrame();
                sellTicketsFrame.setVisible(true);
            }
        });

        btn2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addTicketTypeFrame = new AddTicketTypeFrame();
                addTicketTypeFrame.setVisible(true);
            }
        });
           

        btn3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reserveTicketFrame = new ReserveTicketFrame();
                reserveTicketFrame.setVisible(true);
            }
        });

        btn4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                galleryFrame = new GalleryFrame(admin);
                galleryFrame.setVisible(true);
            }
        });

        btn5.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                summaryFrame = new SummaryFrame();
                summaryFrame.setVisible(true);
            }
        });

        btn6.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        setVisible(true);
    }
}
