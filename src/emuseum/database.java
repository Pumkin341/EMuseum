package emuseum;

import java.sql.*;

public class database {

    private static database instance;
    private Connection con;
    private Statement stmt;

    database() {
        try {
            String url = "jdbc:mysql://localhost:3306/museum";
            String username = "root";
            con = DriverManager.getConnection(url, username, "");
            stmt = con.createStatement();
        } catch (SQLException e) {
            System.out.println(e.getMessage() + "eroare din conexiune");
        }
    }

    public Statement getStatement() {
        return stmt;
    }

    public void view() {
        try {
            String insertquery = "select * from ticket_types";
            ResultSet result = stmt.executeQuery(insertquery);
            if (result.next()) {
                System.out.println("Value " + result.getString(2));
                System.out.println("Value " + result.getString(3));
                System.out.println("value " + result.getString(4));
            }
        } catch (SQLException ex) {
            System.out.println("Problem To Show Data");
        }
    }

    public void close() {
        try {
            stmt.close();
            con.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage() + "eroare la inchidere");
        }
    }

    public static database getInstance() {
        if (instance == null) {
            instance = new database();
        }
        return instance;
    }
}
