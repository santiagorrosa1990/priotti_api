package com.santiago.priotti_api.Item;

import java.util.List;
import java.util.stream.Collectors;

public class ItemQueryBuilder {

    public String build(List<String> keywords){
        String query = "SELECT codigo, aplicacion, marca, rubro, info, precio_lista, precio_oferta, imagen FROM productos WHERE ";
        String out = keywords.stream()
                .map(it -> "(codigo LIKE \"%"+it+"%\" OR  aplicacion Like \"%"+it+"%\" OR" +
                        " marca LIKE \"%"+it+"%\" OR  rubro LIKE \"%"+it+"%\" OR" +
                        "  info LIKE \"%"+it+"%\") AND ")
                .collect(Collectors.joining(""));
        query+=out;
        query+="vigente = 1";
        return query;
    }
}

