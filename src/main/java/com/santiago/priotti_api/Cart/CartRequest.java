package com.santiago.priotti_api.Cart;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;

@Getter
public class CartRequest {

    @SerializedName("id")
    private String id; //id de usuario
    private String item; //FORMATO: MAF12&NOSSO&10
    private String name;
    private String comments;

    public Boolean isErasure(){
        return !(item.split("&").length == 3);
    }

}
