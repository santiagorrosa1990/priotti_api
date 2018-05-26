/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.santiago.priotti_web.Item;

import com.google.gson.Gson;
import com.santiago.priotti_web.Interfaces.Service;
import com.santiago.priotti_web.MySql.MySqlConnector;
import com.santiago.priotti_web.StandardResponse.StandardResponse;
import com.santiago.priotti_web.StandardResponse.StatusResponse;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemService extends MySqlConnector implements Service {
    public static final int LIMIT = 100;

    @Override
    public String getAll() {
        Map<String, Map> itemList = new HashMap<>();
        try {
            Statement st = connect();
            ResultSet rs = st.executeQuery("SELECT * FROM productos LIMIT "+LIMIT);
            while (rs.next()) {
                Map<String, String> item = new HashMap<>();
                item.put("codigo", rs.getString("codigo").trim());
                item.put("aplicacion", rs.getString("aplicacion").trim());
                item.put("rubro", rs.getString("rubro").trim());
                item.put("marca", rs.getString("marca").trim());
                itemList.put(rs.getString("codigo").trim(), item);
            }
        } catch (SQLException e) {
            return "DB EXCEPTION: " + e.getMessage();
        }

        return new Gson().toJson(new StandardResponse(StatusResponse.SUCCESS, itemList));

    }

    public static String getAllAsDatatablesFormat() {
        List<List<String>> itemList = new ArrayList<>();
        try {
            Statement st = connect();
            ResultSet rs = st.executeQuery("SELECT * FROM productos LIMIT "+LIMIT);
            while (rs.next()) {
                itemList.add(Arrays.asList(
                        rs.getString("codigo").trim(),
                        rs.getString("aplicacion").trim(),
                        rs.getString("rubro").trim(),
                        rs.getString("marca").trim()
                ));
            }
            close();
        } catch (SQLException e) {
            return new Gson().toJson(new StandardResponse(StatusResponse.ERROR, "ERROR DB: "+e.getMessage()));
        }
        return new Gson().toJson(new StandardResponse(StatusResponse.SUCCESS, itemList));
    }
    
    //public static String get

}
