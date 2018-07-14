package com.santiago.priotti_api.Wrappers;

import com.google.gson.Gson;
import com.santiago.priotti_api.User.Credentials;
import spark.Request;

import java.util.Map;


public class RequestWrapper {

    Request request;

    public RequestWrapper(Request request) {
        this.request = request;
    }

    public Credentials getCredentials(){
        Map<String, Map<String, String>> requestMap = new Gson().fromJson(this.request.body(), Map.class);
        return Credentials.builder()
                .username(requestMap.get("credentials").get("username"))
                .password(requestMap.get("credentials").get("password"))
                .build();
    }

    public String getBody() {
        return request.body();
    }
}
