package wallet;

import java.sql.*;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);

        int choice;

        User currentUser = null;

        while(true) {

            System.out.println("\n===== DIGITAL WALLET =====");
            System.out.println("1. Create Account");
            System.out.println("2. Login");
            System.out.println("3. Exit");

            System.out.print("Enter: ");

            try {
                choice = input.nextInt();
            } catch(Exception e) {
                System.out.println("Invalid Input!");
                input.nextLine();
                continue;
            }

            switch(choice) {

            case 1:

                input.nextLine();

                System.out.print("Name: ");
                String name = input.nextLine();

                System.out.print("PIN: ");
                String pin = input.nextLine();

                currentUser = new User(name,pin);
                currentUser.saveToDB();

                break;

            case 2:

                input.nextLine();

                System.out.print("Enter PIN: ");
                String loginPin = input.nextLine();

                try {

                    Connection con = DB.connect();

                    PreparedStatement ps = con.prepareStatement(
                        "SELECT * FROM users WHERE pin=?"
                    );

                    ps.setString(1, loginPin);

                    ResultSet rs = ps.executeQuery();

                    if(rs.next()) {

                        currentUser = new User(
                            rs.getString("name"),
                            rs.getString("pin")
                        );

                        currentUser.addMoney(rs.getDouble("balance"));

                        int opt;

                        while(true) {

                            System.out.println("\n--- MENU ---");
                            System.out.println("1. Add Money");
                            System.out.println("2. Send Money");
                            System.out.println("3. Balance");
                            System.out.println("4. Delete Account");
                            System.out.println("5. Logout");

                            System.out.print("Enter: ");

                            try {
                                opt = input.nextInt();
                            } catch(Exception e) {
                                System.out.println("Invalid Input!");
                                input.nextLine();
                                continue;
                            }

                            switch(opt) {

                            case 1:

                                System.out.print("Amount: ");

                                try {
                                    double add = input.nextDouble();
                                    currentUser.addMoney(add);
                                } catch(Exception e) {
                                    System.out.println("Invalid Amount!");
                                    input.nextLine();
                                }

                                break;

                            case 2:

                                System.out.print("Amount: ");

                                try {
                                    double send = input.nextDouble();
                                    currentUser.sendMoney(send);
                                } catch(Exception e) {
                                    System.out.println("Invalid Amount!");
                                    input.nextLine();
                                }

                                break;

                            case 3:

                                currentUser.showBalance();

                                break;

                            case 4:

                                currentUser.deleteAccount();
                                System.out.println("Logging out...");
                                return;

                            case 5:

                                System.out.println("Logged out!");
                                break;

                            }

                            if(opt == 5) break;
                        }

                    } else {
                        System.out.println("Wrong PIN!");
                    }

                } catch(Exception e) {
                    System.out.println("Login Error");
                }

                break;

            case 3:

                System.out.println("Exiting System...");
                System.exit(0);

            }

        }
    }
}