package util;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {
	
    public static Connection getConnection() {
        try {
            String url = "jdbc:mysql://localhost:3306/tourguide";
            String username = "root";
            String password = "123456";

            Class.forName("com.mysql.cj.jdbc.Driver");
            
            return DriverManager.getConnection(url, username, password);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
