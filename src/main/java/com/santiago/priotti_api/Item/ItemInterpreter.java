package com.santiago.priotti_api.Item;

//Se crea una lista completa de los items a partir de la request generada con aprecios.txt, arubrosx.txt y alineasx.txt

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.santiago.priotti_api.Interfaces.Interpreter;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class ItemInterpreter implements Interpreter<Item> {

    @Override
    public List<Item> interpret(String rawBody) {
        Gson gson = new Gson();
        List<Item> itemList = new ArrayList();
        JsonObject json = gson.fromJson(rawBody, JsonObject.class);
        JsonObject lineas = json.get("lineas").getAsJsonObject();
        JsonObject rubros = json.get("rubros").getAsJsonObject();
        JsonArray precios = json.getAsJsonArray("precios");
        //System.out.println(precios);
        for (JsonElement pa : precios) {
            System.out.println("\"002\"".toString()); //TODO ver casteos de String
            System.out.println(002);
            /*JsonObject obj = pa.getAsJsonObject();
            System.out.println(obj.get("linea").toString() + obj.get("rubro").toString());
            //System.out.println("SALIDA "+rubros.get(obj.get("linea").toString() + "" + obj.get("rubro").toString()).toString());
            //String rubro = rubros.get(obj.get("linea").toString() + "" + obj.get("rubro").toString()).toString();
            String linea = lineas.get(obj.get("linea").toString()).toString();
            itemList.add(new Item(
                    obj.get("codigo").toString(),
                    obj.get("aplicacion").toString(),
                    rubro,
                    linea,
                    new BigDecimal(Integer.parseInt(obj.get("aplicacion").toString())))
            );*/
        }
        System.out.println(itemList.toString());
        return new ArrayList<Item>();

    }

    private Type getItemLisToken() {
        Type type = new TypeToken<ArrayList<Item>>() {
        }.getType();
        return type;
    }
}
