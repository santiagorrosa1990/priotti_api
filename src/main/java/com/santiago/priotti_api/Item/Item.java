/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.santiago.priotti_api.Item;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author Santiago
 */
@Builder
@ToString
@Getter
@EqualsAndHashCode
public class Item {

    private String codigo;
    private String aplicacion;
    private String rubro;
    private String linea;
    private String equivalencia;
    private String imagen;
    private BigDecimal precioLista;
    private BigDecimal preciOferta;
    private Date fechaModificado;
    private Date fechaAgregado;

}
