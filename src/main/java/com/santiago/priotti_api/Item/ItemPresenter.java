package com.santiago.priotti_api.Item;

import com.google.gson.Gson;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ItemPresenter {

    public String basicTable(List<Item> itemList){
        List<List<String>> datatablesItemList;
        datatablesItemList = itemList.stream().map(item -> Arrays.asList(
                item.getCodigo(),
                item.getAplicacion(),
                item.getRubro(),
                item.getMarca()))
                .collect(Collectors.toList());
        return new Gson().toJson(datatablesItemList);
    }

    public String fullTable(List<Item> itemList){
        List<List<String>> datatablesItemList;
        datatablesItemList = itemList.stream().map(item -> Arrays.asList(
                item.getCodigo(),
                item.getAplicacion(),
                item.getMarca(),
                item.getRubro(),
                item.getInfo(),
                item.getPrecioLista().toString(),
                item.getPrecioOferta().toString(),
                item.getImagen()))
                .collect(Collectors.toList());
        return new Gson().toJson(datatablesItemList);
    }
}
