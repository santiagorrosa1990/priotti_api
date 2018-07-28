package com.santiago.priotti_api.User;

import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
@ToString
@Getter
@EqualsAndHashCode
public class User {

    private Integer id;
    private String name;
    private String number;
    private String cuit;
    private String email;
    private BigDecimal coeficient;
    @SerializedName("last_login")
    private String lastLogin;
    private Integer visits;
    private String status;
    private Boolean admin;

}
