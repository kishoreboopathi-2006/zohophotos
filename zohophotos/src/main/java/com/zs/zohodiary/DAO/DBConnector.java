package com.zs.zohodiary.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnector {

    private DBConnector() {} 

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); 
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return DriverManager.getConnection(
            "jdbc:mysql://10.52.0.22/zoho_photos",
            "kaviya",
            "kaviya@2007"
        );
    }
}
