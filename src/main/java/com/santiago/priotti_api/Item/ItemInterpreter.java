package com.santiago.priotti_api.Item;

//Se crea una lista completa de los items a partir de la request generada con aprecios.txt, arubrosx.txt y alineasx.txt

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.santiago.priotti_api.Interfaces.Interpreter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


public class ItemInterpreter implements Interpreter<Item> {

    @Override
    public List<Item> interpret(String rawBody) {
        try {
            Gson gson = new Gson();
            List<Item> itemList = new ArrayList<>();
            JsonArray precios = gson.fromJson(rawBody, JsonArray.class);
            for (JsonElement pa : precios) {
                itemList.add(build(pa.getAsJsonObject()));
            }
            return itemList;
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    private Item build(JsonObject json){
        return Item.builder()
                .codigo(json.get("codigo").getAsString())
                .aplicacion(json.get("aplicacion").getAsString())
                .marca(json.get("linea").getAsString())
                .rubro(json.get("rubro").getAsString())
                .precioLista(new BigDecimal(json.get("precio").getAsString()))
                .build();
    }


}
