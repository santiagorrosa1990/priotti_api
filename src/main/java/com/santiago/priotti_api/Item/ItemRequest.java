package com.santiago.priotti_api.Item;

import com.google.gson.annotations.SerializedName;
import com.santiago.priotti_api.Interfaces.Request;
import lombok.Getter;

import java.util.List;

@Getter
public class ItemRequest implements Request {

    @SerializedName("username")
    String username;
    @SerializedName("password")
    String password;
    String keywords;

    @Override
    public boolean hasCredentials() {
        return false;
    }

    //TODO aplicar novedad y oferta

}
