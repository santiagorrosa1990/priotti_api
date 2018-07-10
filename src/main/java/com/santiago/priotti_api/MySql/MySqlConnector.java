/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.santiago.priotti_api.MySql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * @author santiago
 */
public class MySqlConnector { //TODO cambiar por algo no estatico o inyectado

    private static Connection connect = null;
    private static Statement statement = null;
    private static Properties properties = null;

    protected static Statement connect() throws SQLException {
        properties = new Properties();
        properties.setProperty("user", "santiago");
        properties.setProperty("password", "");
        properties.setProperty("useSSL", "false");
        properties.setProperty("autoReconnect", "true");

        connect = DriverManager
                .getConnection("jdbc:mysql://127.0.0.1/db_priotti", properties);

        statement = connect.createStatement();

        return statement;
    }

    // You need to close the resultSet
    protected static void close() throws SQLException {

        if (statement != null) {
            statement.close();
        }

        if (connect != null) {
            connect.close();
        }

    }



}
