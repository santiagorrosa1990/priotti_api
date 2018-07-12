package com.santiago.priotti_api.Item;

import com.google.gson.annotations.SerializedName;
import com.santiago.priotti_api.Interfaces.IRequest;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class ItemRequest implements IRequest {

    @SerializedName("username")  //TODO username y password deben ser un objeto credentials
    String username;
    @SerializedName("password")
    String password;
    @SerializedName("keywords")
    String keywords;

    //TODO aplicar novedad y oferta

    public boolean hasCredentials() {
        return !username.isEmpty() && !password.isEmpty();
    }

    @Override
    public Map<String, String> getCredentials() {
        Map<String, String> credentials = new HashMap<>();
        credentials.put("username", username);
        credentials.put("password", password); //TODO devolver objeto credentials
        return credentials;
    }

    @Override
    public Map<String, String> getPayload() {
        return new HashMap<>();
    }



}
