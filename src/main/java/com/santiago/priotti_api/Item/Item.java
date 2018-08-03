/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.santiago.priotti_api.Item;

import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Santiago
 */
@Builder
@ToString
@Getter
@EqualsAndHashCode
public class Item {

    @EqualsAndHashCode.Include private String codigo;
    @EqualsAndHashCode.Include private String aplicacion;
    @EqualsAndHashCode.Include private String rubro;
    @EqualsAndHashCode.Include private String marca;
    @EqualsAndHashCode.Exclude private String info;
    @EqualsAndHashCode.Exclude private String imagen;
    @SerializedName("precio_lista")
    @EqualsAndHashCode.Include private BigDecimal precioLista;
    @SerializedName("precio_oferta")
    @EqualsAndHashCode.Exclude private BigDecimal precioOferta;
    @SerializedName("fecha_modificado")
    @EqualsAndHashCode.Exclude private Date fechaModificado;
    @SerializedName("fecha_agregado")
    @EqualsAndHashCode.Exclude private Date fechaAgregado;
    @EqualsAndHashCode.Exclude private Integer stock;

}
