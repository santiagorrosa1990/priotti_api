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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author santiago
 */
public class ItemDao extends MySqlConnector implements Dao<Item> {

    public static final int LIMIT = 50;
    public static final String TABLE = "productos_test";

    @Override
    public List<Item> read() throws SQLException {
        return select(0);
    }

    @Override
    public void update(List<Item> newList) throws SQLException {
        List<Item> oldList = select(0);
        close();
        AtomicInteger updates = new AtomicInteger(0);
        AtomicInteger inserts = new AtomicInteger(0);
        Map<String, Item> current = toMap(oldList);
        Statement st = connect();
        System.out.println("Actualizando...");
        newList.forEach(it -> {
            try {
                if (current.containsKey(it.getCodigo())) {
                    if (!current.get(it.getCodigo()).equals(it)) {
                        System.out.println("NEW"+it.toString());
                        System.out.println("OLD"+current.get(it.getCodigo()));
                        update(it, st);
                        updates.getAndIncrement();
                    }
                } else {
                    insert(it, st);
                    inserts.getAndIncrement();
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage()+" "+e.getClass());
            }
        });
        close();
        System.out.println("Listo");
        System.out.println("UPDATES: "+updates);
        System.out.println("INSERTS: "+inserts);
    }

    private Map<String, Item> toMap(List<Item> list) {
        Map<String, Item> map = new HashMap<>();
        list.forEach(it -> map.put(it.getCodigo(), it));
        return map;
    }

    private List<Item> select(int limit) throws SQLException {
        List<Item> itemList = new ArrayList();
        Statement st = connect();
        ResultSet rs;
        if (limit > 0) {
            rs = st.executeQuery("SELECT * FROM " + TABLE + " LIMIT " + limit);
        } else {
            rs = st.executeQuery("SELECT * FROM " + TABLE + "");
        }
        while (rs.next()) {
            itemList.add(Item.builder()
                    .codigo(rs.getString("codigo").trim())
                    .aplicacion(rs.getString("aplicacion").trim())
                    .rubro(rs.getString("rubro").trim())
                    .linea(rs.getString("marca").trim())
                    .precioLista(new BigDecimal(rs.getString("precio_lista").trim()))
                    .build());
        }
        return itemList;
    }

    private void update(Item item, Statement st) throws SQLException {
        String query = "UPDATE " + TABLE + " SET " +
                "codigo = '" + item.getCodigo() +
                "', aplicacion = '" + item.getAplicacion() +
                "', rubro = '" + item.getRubro() +
                "', marca = '" + item.getLinea() +
                "', precio_lista = " + item.getPrecioLista() +
                " WHERE codigo = '" + item.getCodigo()+"'";
        st.executeUpdate(query);
    }

    private void insert(Item item, Statement st) throws SQLException {
        String query = "INSERT INTO " + TABLE + " (codigo, aplicacion, rubro, marca, precio_lista) VALUES ('" + item.getCodigo() +
                "', '" + item.getAplicacion() +
                "', '" + item.getRubro() +
                "', '" + item.getLinea() +
                "', " + item.getPrecioLista() + ")";
        st.executeUpdate(query);
    }


}
