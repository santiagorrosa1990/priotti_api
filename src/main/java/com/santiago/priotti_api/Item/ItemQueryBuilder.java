package com.santiago.priotti_api.Item;

import java.util.List;
import java.util.stream.Collectors;

public class ItemQueryBuilder {

    public String build(List<String> keywords, Boolean offer, Boolean novelty){
        String query = "SELECT codigo, aplicacion, marca, rubro, info, precio_lista, precio_oferta, imagen FROM productos WHERE ";
        String out = keywords.stream()
                .map(it -> "(codigo LIKE \"%"+it+"%\" OR  aplicacion Like \"%"+it+"%\" OR" +
                        " marca LIKE \"%"+it+"%\" OR  rubro LIKE \"%"+it+"%\" OR" +
                        "  info LIKE \"%"+it+"%\") AND ")
                .limit(5)
                .collect(Collectors.joining(""));
        query+=out;
        query+="vigente = 1 AND ";
        if(offer){
            query+="precio_oferta > 0 AND ";
        }
        if(novelty){
            query+="novedad = 1";
        }else{
            query+="novedad = 0";
        }
        return query;
    }
}

