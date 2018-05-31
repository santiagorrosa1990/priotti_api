/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.santiago.priotti_api.Item;

import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author Santiago
 */
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

    public Item(String codigo, String aplicacion, String rubro, String linea, BigDecimal precioLista) {
        this.codigo = codigo;
        this.aplicacion = aplicacion;
        this.rubro = rubro;
        this.linea = linea;
        this.precioLista = precioLista;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getAplicacion() {
        return aplicacion;
    }

    public void setAplicacion(String aplicacion) {
        this.aplicacion = aplicacion;
    }

    public String getRubro() {
        return rubro;
    }

    public void setRubro(String rubro) {
        this.rubro = rubro;
    }

    public String getLinea() {
        return linea;
    }

    public void setLinea(String linea) {
        this.linea = linea;
    }

    public String getEquivalencia() {
        return equivalencia;
    }

    public void setEquivalencia(String equivalencia) {
        this.equivalencia = equivalencia;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public BigDecimal getPrecioLista() {
        return precioLista;
    }

    public void setPrecioLista(BigDecimal precioLista) {
        this.precioLista = precioLista;
    }

    public BigDecimal getPreciOferta() {
        return preciOferta;
    }

    public void setPreciOferta(BigDecimal preciOferta) {
        this.preciOferta = preciOferta;
    }

    public Date getFechaModificado() {
        return fechaModificado;
    }

    public void setFechaModificado(Date fechaModificado) {
        this.fechaModificado = fechaModificado;
    }

    public Date getFechaAgregado() {
        return fechaAgregado;
    }

    public void setFechaAgregado(Date fechaAgregado) {
        this.fechaAgregado = fechaAgregado;
    }

    @Override
    public String toString() {
        return "Item{" +
                "codigo='" + codigo + '\'' +
                ", aplicacion='" + aplicacion + '\'' +
                ", rubro='" + rubro + '\'' +
                ", linea='" + linea + '\'' +
                ", precioLista=" + precioLista +
                '}';
    }
}
