package com.progforce.util;

import com.progforce.exception.DataProcessingException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new DataProcessingException("Can't find MySQL Driver", e);
        }
    }

    public static Connection getConnection() {
        Properties connectionProps = new Properties();
        connectionProps.put("user","root");
        connectionProps.put("password", "1111");
        String url = "jdbc:mysql://localhost:3306/progforce?serverTimezone=UTC";
        try {
            return DriverManager.getConnection(url, connectionProps);
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get connection to DB", e);
        }
    }
}
