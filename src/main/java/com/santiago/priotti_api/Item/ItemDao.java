/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.santiago.priotti_api.Item;

import com.santiago.priotti_api.Interfaces.Dao;
import com.santiago.priotti_api.MySql.MySqlConnector;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author santiago
 */
public class ItemDao extends MySqlConnector implements Dao<Item> {
    
    public static final int LIMIT = 100;

    @Override
    public List<Item> get() throws SQLException {
        List<Item> itemList = new ArrayList();
        Statement st = connect();
        ResultSet rs = st.executeQuery("SELECT * FROM productos LIMIT " + LIMIT);
        while (rs.next()) {
            itemList.add(new Item(rs.getString("codigo").trim(),
                    rs.getString("aplicacion").trim(),
                    rs.getString("rubro").trim(),
                    rs.getString("marca").trim(),
                    new BigDecimal(rs.getString("precio").trim())));
        }
        return itemList;
    }

    @Override
    public void update(List<Item> upToDateList) throws SQLException {
        List<Item> itemList = new ArrayList();
        Statement st = connect();
        ResultSet rs = st.executeQuery("SELECT * FROM productos");

    }


}
