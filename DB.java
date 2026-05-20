package wallet;

import java.sql.Connection;
import java.sql.DriverManager;

public class DB {

    public static Connection connect() {

        try {

            return DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/digital_wallet",
                "root",
                ""
            );

        } catch(Exception e) {

            System.out.println("DB Connection Failed");
            return null;
        }
    }
}