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
import com.santiago.priotti_api.Interfaces.Translator;
import com.santiago.priotti_api.StandardResponse.StandardResponse;
import com.santiago.priotti_api.StandardResponse.StatusResponse;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ItemService implements Service<ItemRequest> {

    private final Dao<Item> itemDao;
    private final Translator<Item> translator;

    @Inject
    public ItemService(Dao<Item> itemDao, Translator<Item> translator) {
        this.itemDao = itemDao;
        this.translator = translator;
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
    public void updateAll(String body) {
        List<Item> itemsList = translator.translateList(body);
        try {
            itemDao.update(itemsList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getSearch(String body) {
        System.out.println(body);
        List<String> keywords = new Gson().fromJson(body, ArrayList.class);
        List<List<String>> datatablesItemList;
        try {
            List<Item> itemList = itemDao.search(keywords);
            datatablesItemList = itemList.stream().map(item -> Arrays.asList(
                    item.getCodigo(),
                    item.getAplicacion(),
                    item.getRubro(),
                    item.getMarca(),
                    item.getInfo(),
                    "$"+item.getPrecioLista().toString(), //TODO sacar el $ de aca
                    "$"+item.getPrecioOferta().toString(),
                    item.getImagen()))
                    .collect(Collectors.toList());
            return new Gson().toJson(datatablesItemList);
        } catch (SQLException ex) {
            return new Gson().toJson(new StandardResponse(StatusResponse.ERROR, "Error: " + ex.getMessage()));
        }
    }

    @Override
    public String getAllAsDatatablesFormat() {
        List<List<String>> datatablesItemList;
        try {
            List<Item> itemList = itemDao.read();
            datatablesItemList = itemList.stream().map(item -> Arrays.asList(
                    item.getCodigo(),
                    item.getAplicacion(),
                    item.getRubro(),
                    item.getMarca(),
                    item.getInfo(),
                    "$"+item.getPrecioLista().toString(), //TODO sacar el $ de aca
                    "$"+item.getPrecioOferta().toString(),
                    item.getImagen()))
                    .collect(Collectors.toList());
            return new Gson().toJson(datatablesItemList);
        } catch (SQLException ex) {
            return new Gson().toJson(new StandardResponse(StatusResponse.ERROR, "Error: " + ex.getMessage()));
        }
    }

    /*@Override
    public String getAllAsDatatablesFormat() {
         List<Map<String,String>> datatablesObject;
        try {
            List<Item> itemList = itemDao.read();
            return "{\"data\":"+ new Gson().toJson(itemList) +"}"; //TODO ver algo mejor para esto (o no)
        } catch (SQLException ex) {
            return new Gson().toJson(new StandardResponse(StatusResponse.ERROR, "Error: " + ex.getMessage()));
        }
    }*/

}