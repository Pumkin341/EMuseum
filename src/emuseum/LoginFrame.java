package emuseum;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class LoginFrame extends JFrame {

    database db = database.getInstance();
    private JTextField usernameField = new JTextField(10);
    private JPasswordField passwordField = new JPasswordField(10);

    public LoginFrame() {
        setTitle("Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 200);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        add(panel);

        JLabel titleLabel = new JLabel("Login");
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        panel.add(titleLabel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new GridLayout(2, 2));
        panel.add(centerPanel, BorderLayout.CENTER);

        JLabel usernameLabel = new JLabel("Username");
        centerPanel.add(usernameLabel);

        centerPanel.add(usernameField);

        JLabel passwordLabel = new JLabel("Password");
        centerPanel.add(passwordLabel);

        centerPanel.add(passwordField);

        JPanel southPanel = new JPanel();
        panel.add(southPanel, BorderLayout.SOUTH);

        JButton loginBtn = new JButton("Login");
        southPanel.add(loginBtn);

        JButton exitBtn = new JButton("Exit");
        southPanel.add(exitBtn);

        loginBtn.addActionListener(e -> login(usernameField.getText(), new String(passwordField.getPassword())));
        exitBtn.addActionListener(e -> dispose());

        setVisible(true);
    }

    public void login(String username, String password) {
        try {
            String query = "select * from users where username = '" + username + "' and password = '" + password + "'";
            ResultSet result = db.getStatement().executeQuery(query);

            if (result.next()) {
                int admin = result.getInt("admin");
                new GUIButtons(admin);
                dispose();
            } else {
                JOptionPane.showMessageDialog(null, "Invalid username or password");
            }

        } catch (SQLException ex) {
            System.out.println("Problem To Show Data");
        }
    }

}
