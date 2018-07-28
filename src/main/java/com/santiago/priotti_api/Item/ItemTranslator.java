package com.santiago.priotti_api.Item;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.santiago.priotti_api.Cart.CartRequest;
import com.santiago.priotti_api.Interfaces.Translator;

import java.lang.reflect.Type;
import java.util.List;

public class ItemTranslator implements Translator<Item, ItemRequest> {

    @Override
    public List<Item> translateList(String body) { //TODO debe ir el request acá
        Gson gson = new Gson();
        Type type = new TypeToken<List<Item>>() {}.getType();
        return gson.fromJson(body, type);
    }

    public ItemRequest translate(String body){
        return new Gson().fromJson(body, ItemRequest.class);
    }

    public Item buildItem(String body){
        return new Gson().fromJson(body, Item.class);
    }

    public CartRequest translateCart(String body){
        return new Gson().fromJson(body, CartRequest.class);
    }
}
