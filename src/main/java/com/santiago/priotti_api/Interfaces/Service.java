/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.santiago.priotti_api.Interfaces;

/**
 *
 * @author santiago
 * @param <T>
 */
public interface Service {

    String getAll();
    
    String getAllAsDatatablesFormat();

    void update(String body);

}
