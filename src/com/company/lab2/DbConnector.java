package com.company.lab2;

import java.sql.Connection;
import java.sql.DriverManager;

public class DbConnector {

    private static final String DRIVER = "com.mysql.jdbc.Driver";
    private static final String CONN_URL = "jdbc:mysql://localhost:3306/skulim_db";
    private static final String PASSWORD = "reter1995";
    private static final String USER = "root";

    public static Connection getConnection(){
        try{
            Connection connection = DriverManager.getConnection(CONN_URL, USER, PASSWORD);
            return connection;
        }catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }


}
