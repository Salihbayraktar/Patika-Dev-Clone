package com.patikadev.Helper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnector {
    private Connection connection = null;

    public Connection connectDb(){
        try {
            //this.connection = DriverManager.getConnection(Config.DB_URL, Config.DB_USERNAME, Config.DB_PASSWORD);
            connection = DriverManager.getConnection(Config.DB_URL);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return this.connection;
    }

    public static Connection getInstance(){
        DBConnector db = new DBConnector();
        return db.connectDb();
    }
}
