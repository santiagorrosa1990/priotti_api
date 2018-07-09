package com.santiago.priotti_api.Item;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;

import java.util.List;

@Getter
public class ItemRequest {

    @SerializedName("username")
    String username;
    @SerializedName("password")
    String password;
    //List<String> keywords;

    //TODO aplicar novedad y oferta

}
