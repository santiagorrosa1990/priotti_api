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

    @Override
    public List<Item> get() throws SQLException {
        return select(LIMIT);
    }

    @Override
    public void update(List<Item> upToDateList) throws SQLException {
        List<Item> itemList = select(0);
        AtomicInteger cont = new AtomicInteger();
        Map<String, Item> actual = toMap(itemList);
        upToDateList.forEach(it -> {
            if(actual.containsKey(it.getCodigo())){
                if(actual.get(it.getCodigo()).equals(it)){

                }else{
                    //update(it);
                    System.out.println(cont+"// "+it.getCodigo());
                    cont.getAndIncrement();
                }
            }else{
                System.out.println("No existe");
                //insert(it);
            }
        });

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
        if(limit > 0){
            rs = st.executeQuery("SELECT * FROM productos LIMIT " +limit);
        }else{
            rs = st.executeQuery("SELECT * FROM productos");
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

    private void update(Item item) throws SQLException {
        Statement st = connect();
        //st.executeQuery("UPDATE productos SET ...bla bla WHERE codigo = bla bla"); //TODO terminar el update
    }

    private void insert(Item item) throws SQLException{
        Statement st = connect();
        //st.executeQuery("INSERT INTO productos (codigo, aplicacion, rubro, marca, linea, precio) VALUES (bla bla bla)"); //TODO verificar nombre de las columnas de la bd
    }



}
