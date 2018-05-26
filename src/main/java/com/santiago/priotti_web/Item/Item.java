/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.santiago.priotti_web.Item;

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
    private String marca;
    private String equivalencia;
    private String imagen;
    private BigDecimal precio_lista;
    private BigDecimal precio_oferta;
    private Date fecha_modif;
    private Date fecha_agregado;

    public Item(String codigo, String aplicacion, String rubro, String marca, String equivalencia) {
        this.codigo = codigo;
        this.aplicacion = aplicacion;
        this.rubro = rubro;
        this.marca = marca;
        this.equivalencia = equivalencia;
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

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
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

    public BigDecimal getPrecio_lista() {
        return precio_lista;
    }

    public void setPrecio_lista(BigDecimal precio_lista) {
        this.precio_lista = precio_lista;
    }

    public BigDecimal getPrecio_oferta() {
        return precio_oferta;
    }

    public void setPrecio_oferta(BigDecimal precio_oferta) {
        this.precio_oferta = precio_oferta;
    }

    public Date getFecha_modif() {
        return fecha_modif;
    }

    public void setFecha_modif(Date fecha_modif) {
        this.fecha_modif = fecha_modif;
    }

    public Date getFecha_agregado() {
        return fecha_agregado;
    }

    public void setFecha_agregado(Date fecha_agregado) {
        this.fecha_agregado = fecha_agregado;
    }
    
    
    

    

}
