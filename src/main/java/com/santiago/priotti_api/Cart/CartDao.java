package com.santiago.priotti_api.Cart;

import com.google.gson.Gson;
import com.santiago.priotti_api.MySql.MySqlConnector;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class CartDao extends MySqlConnector {

    public static final int LIMIT = 100;
    public static final String TABLE = "productos";

    public String removeFromCart(CartRequest request) throws SQLException {
        List<String> toBeRemoved = splitOnAmpersand(request.getItem());
        ResultSet rs = getFromCart(request);
        List<List<String>> list = split(rs);
        List<List<String>> filteredList = list.stream().filter(item -> !item.get(0).equals(toBeRemoved.get(0))).collect(Collectors.toList());
        String order = convert(filteredList);
        save(order, request);
        close();
        filteredList = applyStockCriteria(filteredList);
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
        list = applyStockCriteria(list);
        return new Gson().toJson(list);
    }

    public String getOrder(CartRequest request) throws SQLException {
        ResultSet rs = getFromCart(request);
        List<List<String>> cart = split(rs);
        cart = applyStockCriteria(cart);
        String order = new Gson().toJson(cart);
        close();
        return order;
    }

    public List getOrderHistory(CartRequest request) throws SQLException {
        String select = "SELECT * FROM pedidos WHERE cliente = " + request.getId() + " AND estado= 'LISTO'";
        Statement st = connect();
        ResultSet rs = st.executeQuery(select);
        List<List<String>> historyList = new ArrayList<>();
        while (rs.next()) {
            String date = rs.getString("fechapedido");
            String items = rs.getString("items").replace("&", "--")
                    .replace(",", "<br>");
            historyList.add(Arrays.asList(date, items));
        }
        close();
        return historyList;
    }

    public List<List<String>> closeOrder(CartRequest request) throws SQLException {
        ResultSet rs = getFromCart(request);
        List<List<String>> list = split(rs);
        Statement st = connect();
        String query = "UPDATE pedidos SET estado = 'LISTO', " +
                "fechapedido = '" + LocalDateTime.now() + "' WHERE cliente = " + request.getId() + " AND estado = 'PENDIENTE'";
        st.executeUpdate(query);
        close();
        return list;
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

    private List<String> splitOnAmpersand(String item) {
        return Arrays.asList(item.split("&"));
    }

    private List applyStockCriteria(List<List<String>> fixedList) { //Arrays.asList returns an immutable list so whe must create another list with stock added
        List<List<String>> list = fixedList.stream()
                .map(item -> Arrays.asList(item.get(0), item.get(1), item.get(2), getStockAvailability(item.get(0)).toString())).collect(Collectors.toList());
        return list;
    }

    private Integer getStockAvailability(String code) {
        try {
            Statement st = connect();
            ResultSet rs;
            Integer quantity = 0;
            String query = "SELECT stock FROM productos WHERE codigo = '" + code + "'";
            rs = st.executeQuery(query);
            while (rs.next()) {
                quantity = rs.getInt("stock");
            }
            close();
            return quantity;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

}
