package com.santiago.priotti_api.User;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;

@Builder
@ToString
@Getter
@EqualsAndHashCode
public class User {

    private Integer id;
    private String name;
    private String number;
    private String cuit;
    private BigDecimal coeficient;

}
