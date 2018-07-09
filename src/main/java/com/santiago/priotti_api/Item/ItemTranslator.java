package com.santiago.priotti_api.Item;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.santiago.priotti_api.Interfaces.Translator;
import spark.Request;

import java.lang.reflect.Type;
import java.util.List;

public class ItemTranslator implements Translator<Item, ItemRequest> {

    @Override
    public List<Item> translateList(String body) { //TODO debe ir el request acá
        Gson gson = new Gson();
        Type type = new TypeToken<List<Item>>() {}.getType();
        return gson.fromJson(body, type);
    }

    public ItemRequest translate(Request request){
        Gson gson = new Gson();
        Type type = new TypeToken<ItemRequest>() {}.getType();
        return gson.fromJson(request.body(), type);
    }

    @Override
    public Item translateSearchRequest(String body, String p) {
        return null;
    }
}
