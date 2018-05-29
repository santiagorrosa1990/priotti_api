/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.santiago.priotti_api.Item;

import com.google.gson.Gson;
import com.google.inject.Inject;
import com.santiago.priotti_api.Interfaces.Dao;
import com.santiago.priotti_api.Interfaces.Service;
import com.santiago.priotti_api.MySql.MySqlConnector;
import com.santiago.priotti_api.StandardResponse.StandardResponse;
import com.santiago.priotti_api.StandardResponse.StatusResponse;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
        List<List<String>> dtItemList;
        try {
            List<Item> itemList = itemDao.get() ;
            dtItemList = itemList.stream().map(item -> Arrays.asList(item.getCodigo(), item.getAplicacion(), item.getRubro(), item.getMarca())).collect(Collectors.toList());
            return new Gson().toJson(new StandardResponse(StatusResponse.SUCCESS, dtItemList));
        } catch (SQLException ex) {
            return new Gson().toJson(new StandardResponse(StatusResponse.ERROR, "Error: "+ex.getMessage()));
        }
    }

}