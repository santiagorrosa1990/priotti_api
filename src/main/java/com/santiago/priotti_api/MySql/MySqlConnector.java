/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.santiago.priotti_api.MySql;

import com.google.gson.Gson;
import com.santiago.priotti_api.Utils.FileReader;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;
import java.util.Properties;

/**
 * @author santiago
 */
public class MySqlConnector { //TODO cambiar por algo no estatico o inyectado

    private static Connection connect = null;
    private static Statement statement = null;
    private static Properties properties = null;

    protected static Statement connect() throws SQLException {
        /*properties = new Properties();
        properties.setProperty("user", "santiago");
        properties.setProperty("password", "Tato1432");
        properties.setProperty("useSSL", "false");
        properties.setProperty("autoReconnect", "true");*/

        String json = new FileReader().getFile("com/santiago/priotti_api/config.json");
        Map<String, String> map = new Gson().fromJson(json, Map.class);


        properties = new Properties();
        properties.setProperty("user", map.get("user"));
        properties.setProperty("password", map.get("password"));
        properties.setProperty("useSSL", map.get("userSSL"));
        properties.setProperty("autoReconnect", map.get("autoReconnect"));

        connect = DriverManager
                .getConnection(map.get("db_type"), properties);

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
