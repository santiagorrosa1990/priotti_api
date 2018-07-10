package com.santiago.priotti_api.User;

import com.google.gson.Gson;
import com.santiago.priotti_api.Item.ItemRequest;
import spark.Request;

import java.util.HashMap;
import java.util.Map;

public class UserAuthenticator {

    public Boolean authenticate(ItemRequest request){
        if(request.getUsername().equals("srosa")&& request.getPassword().equals("1432")){ //TODO continuar
            return true;
        }
        return false;
    }
}
