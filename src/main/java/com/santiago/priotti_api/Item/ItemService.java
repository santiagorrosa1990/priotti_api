/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.santiago.priotti_api.Item;

import com.google.gson.Gson;
import com.google.inject.Inject;
import com.santiago.priotti_api.Interfaces.Translator;
import com.santiago.priotti_api.StandardResponse.StandardResponse;
import com.santiago.priotti_api.StandardResponse.StatusResponse;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ItemService {

    private final ItemDao itemDao;
    private final Translator<Item, ItemRequest> translator; // TODO ver si mandar el translator al controller

    @Inject
    public ItemService(ItemDao itemDao, Translator<Item, ItemRequest> translator) {
        this.itemDao = itemDao;
        this.translator = translator;
    }

    public void updateAll(String body) {
        List<Item> itemsList = translator.translateList(body);
        try {
            itemDao.update(itemsList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String fullSearch(ItemRequest request) {
        Boolean novelty = request.getNovelty();
        Boolean offer = request.getOffer();
        List keywords = getKeywords(request.getKeywords());
        String query = new ItemQueryBuilder().build(keywords, offer, novelty);
        try {
            List<Item> itemList = itemDao.search(query);
            return new ItemPresenter().fullTable(itemList);

        } catch (SQLException ex) {
            return new Gson().toJson(new StandardResponse(StatusResponse.ERROR, "Error: " + ex.getMessage()));
        }
    }

    public String basicSearch(ItemRequest request) {
        Boolean novelty = request.getNovelty();
        Boolean offer = request.getOffer();
        List keywords = getKeywords(request.getKeywords());
        String query = new ItemQueryBuilder().build(keywords, offer, novelty);
        try {
            List<Item> itemList = itemDao.search(query);
            return new ItemPresenter().basicTable(itemList);

        } catch (SQLException ex) {
            return new Gson().toJson(new StandardResponse(StatusResponse.ERROR, "Error: " + ex.getMessage()));
        }
    }

    public String getAll() {
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

    private List<String> getKeywords(String search){
        String[] words = search.split("\\s+");
        return Arrays.asList(words);
    }



}