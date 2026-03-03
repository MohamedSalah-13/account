package com.hamza.account;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnection {

    public static final String DATABASE_URL = "jdbc:mysql://localhost:3306/sales_db";
    public static final String USERNAME = "root";
    public static final String PASSWORD = "1234";
    public static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static Connection connection;

    public static Connection getConnection() {
        try {
            if (connection==null || connection.isClosed()){
                Class.forName(DRIVER);
                connection = DriverManager.getConnection(DATABASE_URL,USERNAME,PASSWORD);
                System.out.println("Connection established successfully");
            }
        }catch(ClassNotFoundException | java.sql.SQLException e) {
            System.out.println("Failed to establish database connection");
        }

        return connection;
    }
}
