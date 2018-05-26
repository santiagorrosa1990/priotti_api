/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.santiago.priotti_web.MySql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author santiago
 */
public class MySqlConnector {

    private static Connection connect = null;
    private static Statement statement = null;
    private static final String USER = "SANTIAGO";
    private static final String PASS = "Tato1432";

    protected static Statement connect() throws SQLException {
        connect = DriverManager
                .getConnection("jdbc:mysql://localhost/db_priotti", USER, PASS);

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
