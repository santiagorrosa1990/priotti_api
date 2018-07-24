package com.santiago.priotti_api.Item;

import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class ItemRequest {

    private String keywords; //TODO iria una lista de string
    private Boolean novelty;
    private Boolean offer;
    private BigDecimal coeficient;

}
