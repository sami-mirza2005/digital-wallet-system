package wallet;

import java.sql.*;

public class User {

    private String name;
    private String pin;
    private double balance;

    public User(String name, String pin) {
        this.name = name;
        this.pin = pin;
        this.balance = 0;
    }

    // CREATE ACCOUNT
    public void saveToDB() {

        try {

            Connection con = DB.connect();

            PreparedStatement ps = con.prepareStatement(
                "INSERT INTO users(name,pin,balance) VALUES(?,?,?)"
            );

            ps.setString(1, name);
            ps.setString(2, pin);
            ps.setDouble(3, balance);

            ps.executeUpdate();

            System.out.println("Account Created!");

        } catch(Exception e) {
            System.out.println("Error");
        }
    }

    // ADD MONEY
    public void addMoney(double amount) {

        balance += amount;
        updateBalance();
    }

    // SEND MONEY
    public void sendMoney(double amount) {

        if(balance >= amount) {

            balance -= amount;
            updateBalance();

        } else {
            System.out.println("Insufficient Balance!");
        }
    }

    // UPDATE DB
    public void updateBalance() {

        try {

            Connection con = DB.connect();

            PreparedStatement ps = con.prepareStatement(
                "UPDATE users SET balance=? WHERE name=? AND pin=?"
            );

            ps.setDouble(1, balance);
            ps.setString(2, name);
            ps.setString(3, pin);

            ps.executeUpdate();

        } catch(Exception e) {
            System.out.println("Update Error");
        }
    }

    public void showBalance() {
        System.out.println("Balance: " + balance);
    }

    // DELETE ACCOUNT
    public void deleteAccount() {

        try {

            Connection con = DB.connect();

            PreparedStatement ps = con.prepareStatement(
                "DELETE FROM users WHERE name=? AND pin=?"
            );

            ps.setString(1, name);
            ps.setString(2, pin);

            ps.executeUpdate();

            System.out.println("Account Deleted!");

        } catch(Exception e) {
            System.out.println("Delete Error");
        }
    }
}