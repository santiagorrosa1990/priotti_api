package com.santiago.priotti_api.User;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.santiago.priotti_api.Interfaces.Translator;
import com.santiago.priotti_api.Item.Item;

import java.lang.reflect.Type;
import java.util.List;

public class UserTranslator implements Translator<User, UserRequest> {

    @Override
    public List<User> translateList(String body) { //TODO debe ir el request acá
        Gson gson = new Gson();
        Type type = new TypeToken<List<Item>>() {}.getType();
        return gson.fromJson(body, type);
    }

    @Override
    public UserRequest translate(String body){
        Gson gson = new Gson();
        return gson.fromJson(body, UserRequest.class);
    }

    public User buildUser(String body) {
        return new Gson().fromJson(body, User.class);
    }
}
