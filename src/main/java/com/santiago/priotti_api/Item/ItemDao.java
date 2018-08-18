/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.santiago.priotti_api.Item;

import com.santiago.priotti_api.MySql.MySqlConnector;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author santiago
 */
public class ItemDao extends MySqlConnector {

    public static final int LIMIT = 100;
    public static final String TABLE = "productos";

    //READ

    public List<Item> read() throws SQLException {
        return select(20000);
    }

    public List<Item> search(String query) throws SQLException {
        query = query + " LIMIT " + LIMIT;
        Statement st = connect();
        ResultSet rs = st.executeQuery(query);
        List<Item> itemList = new ItemInterpreter().interpret(rs);
        close();
        return itemList;
    }

    private List<Item> select(int limit) throws SQLException {
        Statement st = connect();
        ResultSet rs;
        rs = st.executeQuery("SELECT * FROM  " + TABLE + " ORDER BY marca, codigo LIMIT " + limit);
        List<Item> itemList = new ItemInterpreter().interpret(rs);
        close();
        return itemList;
    }

    //UPDATE ALL FROM FILE

    public void updateAll(List<Item> updatedList) throws SQLException {
        long lStartTime = new Date().getTime(); // start time
        List<Item> dbList = select(20000);
        AtomicInteger updates = new AtomicInteger(0);
        AtomicInteger inserts = new AtomicInteger(0);
        Map<String, Item> outdatedList = toMap(dbList);
        final Statement st = connect();
        System.out.println("Updating...");
        updatedList.forEach(it -> {
            try {
                if (outdatedList.containsKey(it.getCodigo())) {
                    if (!outdatedList.get(it.getCodigo()).equals(it)) {
                        updates.getAndIncrement();
                        update(it, st);
                    }
                } else {
                    insert(it, st);
                    inserts.getAndIncrement();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        close();
        long lEndTime = new Date().getTime(); // end time
        long difference = lEndTime - lStartTime; // check different
        System.out.println("Elapsed milliseconds: " + difference);
        System.out.println("Updates: " + updates);
        System.out.println("Inserts: " + inserts);
    }

    private Map<String, Item> toMap(List<Item> list) {
        Map<String, Item> map = new HashMap<>();
        list.forEach(it -> map.put(it.getCodigo(), it));
        return map;
    }

    private void update(Item item, Statement st) throws SQLException {
        String query = "UPDATE " + TABLE + " SET " +
                "codigo = '" + item.getCodigo() +
                "', aplicacion = '" + item.getAplicacion() +
                "', rubro = '" + item.getRubro() +
                "', marca = '" + item.getMarca() +
                "', precio_lista = " + item.getPrecioLista() +
                " WHERE codigo = '" + item.getCodigo() + "'";
        st.executeUpdate(query);
    }

    private void insert(Item item, Statement st) throws SQLException {
        String query = "INSERT INTO " + TABLE + " (codigo, aplicacion, rubro, marca, precio_lista) VALUES ('" + item.getCodigo() +
                "', '" + item.getAplicacion() +
                "', '" + item.getRubro() +
                "', '" + item.getMarca() +
                "', " + item.getPrecioLista() + ")";
        st.executeUpdate(query);
    }

    //UPDATE ONE ITEM

    public void updateOne(Item item) throws SQLException {
        Statement st = connect();
        String query = "UPDATE " + TABLE + " SET " +
                "precio_oferta = '" + item.getPrecioOferta() +
                "', info = '" + item.getInfo() +
                "' WHERE codigo = '" + item.getCodigo() + "'";
        st.executeUpdate(query);
        close();
    }

}


