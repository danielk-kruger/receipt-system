package com.project.receiptsystem.db;

import com.intersys.jdbc.CacheDataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class DBService {

    private Connection dbConn;
    private final String url = "jdbc:Cache://197.249.50.94:1972/DEVESSE";
    private final String user = "APP";
    private final String password = "%sys";

    public Connection getConnection() {
        return this.dbConn;
    }

    public void connect() {
        try {

            Class.forName("com.intersys.jdbc.CacheDriver");
            CacheDataSource ds = new CacheDataSource();
            ds.setURL(this.url);
            ds.setUser(this.user);
            ds.setPassword(this.password);
            this.dbConn = ds.getConnection();

            System.out.println("Sucessfully Connected to Database");


        } catch (SQLException e) {
            System.out.println("Error accessing database");
            System.out.println(e.getMessage());
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.out.println("Error loading jdbc driver");
            e.printStackTrace();
        }
    }

    public void close() {
        try {
            this.dbConn.close();
            System.out.println("Successfully closed the database connection.");
        } catch (Exception e) {
            System.out.println("Error closing the database connection.");
            e.printStackTrace();
        }
    }
}
