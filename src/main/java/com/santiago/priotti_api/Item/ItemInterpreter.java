package com.santiago.priotti_api.Item;

//Se crea una lista completa de los items a partir de la request generada con aprecios.txt, arubrosx.txt y alineasx.txt

import com.google.gson.JsonObject;
import com.santiago.priotti_api.Interfaces.Interpreter;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class ItemInterpreter implements Interpreter<Item> {

    @Override
    public List<Item> interpret(ResultSet rs) throws SQLException {
        List<Item> itemList = new ArrayList<>();
        while (rs.next()) {
            itemList.add(Item.builder()
                    .codigo(rs.getString("codigo").trim())
                    .aplicacion(rs.getString("aplicacion").trim())
                    .rubro(rs.getString("rubro").trim())
                    .marca(rs.getString("marca").trim())
                    .info(Optional.ofNullable(rs.getString("info")).orElse("")) //TODO cambiar por equivalencia y ver lo de NULL en MYSQL
                    .precioLista(new BigDecimal(rs.getString("precio_lista").trim()))
                    .precioOferta(new BigDecimal(rs.getString("precio_oferta").trim()))
                    .imagen(rs.getString("imagen").trim())
                    .build());
        }
        return itemList;
    }

}
