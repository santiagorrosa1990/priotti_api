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
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author santiago
 */
public class ItemDao extends MySqlConnector implements Dao<Item> {

    /* Modelo de respuesta para datatables
 {"data":[
            {
            "codigo":"9004",
            "aplicacion":"HB1 12V 65\/45W P29t",
            "marca":"OSRAM",
            "rubro":"LAMPARAS HALOGENAS OSRAM",
            "info":"",
            "precio_lista":"273.88",
            "precio_oferta":"0.00",
            "imagen":"9004"
            },
          ]
          }
    */

    public static final int LIMIT = 100;
    public static final String TABLE = "productos";

    @Override
    public List<Item> read() throws SQLException {
        return select(LIMIT);
    }

    @Override
    public void update(List<Item> updatedList) throws SQLException {
        long lStartTime = new Date().getTime(); // start time
        List<Item> dbList = select(0);
        AtomicInteger updates = new AtomicInteger(0);
        AtomicInteger inserts = new AtomicInteger(0);
        Map<String, Item> outdatedList = toMap(dbList);
        Statement st = connect();
        System.out.println("Updating...");
        updatedList.forEach(it -> {
            try {
                if (outdatedList.containsKey(it.getCodigo())) {
                    if (!outdatedList.get(it.getCodigo()).equals(it)) {
                        //System.out.println("Updated  " + it);
                        updates.getAndIncrement();
                        update(it, st);
                    }
                } else {
                    insert(it, st);
                    inserts.getAndIncrement();
                    //System.out.println("Inserted " + it.toString());
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage() + " " + e.getClass());
            }
        });
        close();
        long lEndTime = new Date().getTime(); // end time
        long difference = lEndTime - lStartTime; // check different
        System.out.println("Elapsed milliseconds: " + difference);
        System.out.println("Updates: " + updates);
        System.out.println("Inserts: " + inserts);
    }

    @Override
    public List<Item> search(List<String> keywords) throws SQLException {
        String query = new ItemQueryBuilder().build(keywords);
        query = query+" LIMIT "+LIMIT;
        List<Item> itemList = new ArrayList<>();
        Statement st = connect();
        ResultSet rs;
        rs = st.executeQuery(query);
        while (rs.next()) {
            itemList.add(Item.builder()
                    .codigo(rs.getString("codigo").trim())
                    .aplicacion(rs.getString("aplicacion").trim())
                    .rubro(rs.getString("rubro").trim())
                    .marca(rs.getString("marca").trim())
                    .info(Optional.ofNullable(rs.getString("info")).orElse("")) //TODO cambiar por equivalencia y ver lo de NULL en MYSQL
                    .precioLista(new BigDecimal(rs.getString("precio_lista").trim()))
                    .precioOferta(new BigDecimal(rs.getString("precio_oferta").trim()))
                    .imagen(rs.getString("imagen").trim())
                    .build());
        }
        close();
        return itemList;
    }

    private Map<String, Item> toMap(List<Item> list) {
        Map<String, Item> map = new HashMap<>();
        list.forEach(it -> map.put(it.getCodigo(), it));
        return map;
    }

    private List<Item> select(int limit) throws SQLException {
        List<Item> itemList = new ArrayList<>();
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
                    .marca(rs.getString("marca").trim())
                    .info(Optional.ofNullable(rs.getString("info")).orElse("")) //TODO cambiar por equivalencia y ver lo de NULL en MYSQL
                    .precioLista(new BigDecimal(rs.getString("precio_lista").trim()))
                    .precioOferta(new BigDecimal(rs.getString("precio_oferta").trim()))
                    .imagen(rs.getString("imagen").trim())
                    .build());
        }
        close();
        return itemList;
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

}


