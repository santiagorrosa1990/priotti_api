/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.santiago.priotti_api.Item;

import com.google.gson.Gson;
import com.google.inject.Inject;
import com.santiago.priotti_api.Interfaces.Dao;
import com.santiago.priotti_api.Interfaces.Interpreter;
import com.santiago.priotti_api.Interfaces.Service;
import com.santiago.priotti_api.MySql.MySqlConnector;
import com.santiago.priotti_api.StandardResponse.StandardResponse;
import com.santiago.priotti_api.StandardResponse.StatusResponse;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class ItemService implements Service {

    private final Dao<Item> itemDao;
    private final Interpreter<Item> interpreter;

    @Inject
    public ItemService(Dao<Item> itemDao, Interpreter<Item> interpreter) {
        this.itemDao = itemDao;
        this.interpreter = interpreter;
    }

    @Override
    public String getAll() {
        List<Item> itemList;
        try {
            itemList = itemDao.read();
            return new Gson().toJson(new StandardResponse<List>(StatusResponse.SUCCESS, itemList));
        } catch (SQLException ex) {
            return new Gson().toJson(new StandardResponse(StatusResponse.ERROR, "Error: " + ex.getMessage()));
        }
    }

    @Override
    public String getAllAsDatatablesFormat() {
        List<List<String>> datatablesItemList;
        try {
            List<Item> itemList = itemDao.read();
            datatablesItemList = itemList.stream().map(item -> Arrays.asList(item.getCodigo(), item.getAplicacion(), item.getRubro(), item.getLinea())).collect(Collectors.toList());
            return new Gson().toJson(new StandardResponse<List>(StatusResponse.SUCCESS, datatablesItemList));
        } catch (SQLException ex) {
            return new Gson().toJson(new StandardResponse(StatusResponse.ERROR, "Error: " + ex.getMessage()));
        }
    }

    @Override
    public void update(String body) {
        List<Item> itemsList = interpreter.interpret(body);
        try {
            itemDao.update(itemsList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}