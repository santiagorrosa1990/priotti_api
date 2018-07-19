package com.santiago.priotti_api.Wrappers;

import com.google.gson.Gson;
import com.santiago.priotti_api.User.Credentials;
import spark.Request;

import java.util.Map;
import java.util.Optional;


public class RequestWrapper {

    Request request;

    public RequestWrapper(Request request) {
        this.request = request;
    }

    public Credentials getCredentials(){
        Map requestMap = new Gson().fromJson(this.request.body(), Map.class);
        return Credentials.builder()
                .username((String) ((Map)requestMap.get("credentials")).get("username")) //TODO ver este casteo
                .password((String) ((Map)requestMap.get("credentials")).get("password"))
                .build();
    }

    public String getToken(){
        Map requestMap = new Gson().fromJson(this.request.body(), Map.class);
        return Optional.ofNullable((String)requestMap.get("token")).orElse("");
    }

    public String getBody() {
        return request.body();
    }
}