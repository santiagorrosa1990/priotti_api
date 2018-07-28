/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.santiago.priotti_api.Item;

import com.google.gson.Gson;
import com.santiago.priotti_api.Cart.CartRequest;
import com.santiago.priotti_api.MySql.MySqlConnector;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * @author santiago
 */
public class ItemDao extends MySqlConnector {

    public static final int LIMIT = 100;
    public static final String TABLE = "productos";

    //READ

    public List<Item> read() throws SQLException {
        return select(LIMIT);
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
        rs = st.executeQuery("SELECT * FROM " + TABLE + " LIMIT " + limit);
        List<Item> itemList = new ItemInterpreter().interpret(rs);
        close();
        return itemList;
    }

    //UPDATE ALL FROM FILE

    public void updateAll(List<Item> updatedList) throws SQLException {
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

    //UPDATE

    public void updateOne(Item item) throws SQLException{
        Statement st = connect();
        String query = "UPDATE " + TABLE + " SET " +
                "precio_oferta = '" + item.getPrecioOferta() +
                "', info = '" + item.getInfo() +
                "' WHERE codigo = '" + item.getCodigo() + "'";
        st.executeUpdate(query);
    }

    //CART ABM

    public String removeFromCart(CartRequest request) throws SQLException {
        List<String> toBeRemoved = splitOnAmpersand(request.getItem());
        ResultSet rs = getFromCart(request);
        List<List<String>> list = split(rs);
        List<List<String>> filteredList = list.stream().filter(item -> !item.get(0).equals(toBeRemoved.get(0))).collect(Collectors.toList());
        String order = convert(filteredList);
        save(order, request);
        close();
        return new Gson().toJson(filteredList);
    }

    public String addToCart(CartRequest request) throws SQLException {
        List<String> toBeAdded = splitOnAmpersand(request.getItem());
        ResultSet rs = getFromCart(request);
        List<List<String>> list = split(rs);
        if (list.stream().noneMatch(item -> item.get(0).equals(toBeAdded.get(0)))) {
            list.add(toBeAdded);
        } else {
            Optional<List<String>> item = list.stream().filter(it -> it.get(0).equals(toBeAdded.get(0))).findFirst();
            item.ifPresent(strings -> strings.set(2, toBeAdded.get(2)));
        }
        String order = convert(list);
        save(order, request);
        close();
        return new Gson().toJson(list);
    }

    public String getOrder(CartRequest request) throws SQLException {
        ResultSet rs = getFromCart(request);
        String order = new Gson().toJson(split(rs));
        close();
        return order;
    }

    private ResultSet getFromCart(CartRequest request) throws SQLException {
        String select = "SELECT * FROM pedidos WHERE cliente = " + request.getId() + " AND estado= 'PENDIENTE'";
        Statement st = connect();
        ResultSet rs = st.executeQuery(select);
        if (!rs.next()) {
            String insert = "INSERT INTO pedidos(estado, cliente, items) VALUES ('PENDIENTE', " + request.getId() + ", '')";
            st.executeUpdate(insert);
        }
        rs = st.executeQuery(select);  //TODO ver esta lógica media boba
        rs.next();
        return rs;
    }

    private void save(String order, CartRequest request) throws SQLException {
        Statement st = connect();
        String query = "UPDATE pedidos SET items = '" + order + "' WHERE cliente = " + request.getId() + " AND estado= 'PENDIENTE'";
        st.executeUpdate(query);
    }

    private String convert(List<List<String>> list) {
        List<String> first = list.stream().map(it -> it.get(0) + "&" + it.get(1) + "&" + it.get(2)).collect(Collectors.toList());
        return first.stream().collect(Collectors.joining(","));
    }

    private List<List<String>> split(ResultSet rs) throws SQLException {
        String order = rs.getString("items").trim();
        if (order.isEmpty()) return new ArrayList<>();
        List<String> splitted = Arrays.asList(order.split(","));
        return splitted.stream().map(this::splitOnAmpersand).collect(Collectors.toList());
    }

    public List<List<String>> closeOrder(CartRequest request) throws SQLException {
        ResultSet rs = getFromCart(request);
        List<List<String>> list = split(rs);
        Statement st = connect();
        String query = "UPDATE pedidos SET estado = 'LISTO', " +
                "fechapedido = '"+ LocalDateTime.now() +"' WHERE cliente = " + request.getId()+" AND estado = 'PENDIENTE'";
        st.executeUpdate(query);
        close();
        return list;
    }

    private List<String> splitOnAmpersand(String item) {
        return Arrays.asList(item.split("&"));
    }

    public List getOrderHistory(CartRequest request) throws SQLException {
        String select = "SELECT * FROM pedidos WHERE cliente = " + request.getId() + " AND estado= 'LISTO'";
        Statement st = connect();
        ResultSet rs = st.executeQuery(select);
        List<List<String>> historyList = new ArrayList<>();
        while(rs.next()){
            String date = rs.getString("fechapedido");
            String items = rs.getString("items").replace("&","--")
                    .replace(",", "<br>");
            historyList.add(Arrays.asList(date, items));
        }
        close();
        return historyList;
    }
}


