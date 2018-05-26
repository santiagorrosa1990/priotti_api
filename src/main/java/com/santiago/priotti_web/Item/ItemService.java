/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.santiago.priotti_web.Item;

import com.google.gson.Gson;
import com.google.inject.Inject;
import com.santiago.priotti_web.Interfaces.Dao;
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
import java.util.logging.Level;
import java.util.logging.Logger;

public class ItemService extends MySqlConnector implements Service {

    private final Dao itemDao;
    

    @Inject
    public ItemService(Dao itemDao) {
        this.itemDao = itemDao;
    }

    @Override
    public String getAll() {
        List<Item> itemList;
        try {
            itemList = itemDao.get();
            return new Gson().toJson(new StandardResponse(StatusResponse.SUCCESS, itemList));
        } catch (SQLException ex) {
            return new Gson().toJson(new StandardResponse(StatusResponse.ERROR, "Error: "+ex.getMessage()));
        }
    }

    public String getAllAsDatatablesFormat() {
        List<List<String>> dtItemList = new ArrayList();
        try {
            List<Item> itemList = itemDao.get() ;
            for(Item item : itemList){
                dtItemList.add(Arrays.asList(item.getCodigo(), item.getAplicacion(), item.getRubro(), item.getMarca()));
            }
            return new Gson().toJson(new StandardResponse(StatusResponse.SUCCESS, dtItemList));
        } catch (SQLException ex) {
            return new Gson().toJson(new StandardResponse(StatusResponse.ERROR, "Error: "+ex.getMessage()));
        }
    }

}
