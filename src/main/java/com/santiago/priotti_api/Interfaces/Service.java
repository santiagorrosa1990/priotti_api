/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.santiago.priotti_api.Interfaces;

import com.santiago.priotti_api.Item.ItemRequest;

/**
 *
 * @author santiago
 * @param <T>
 */
public interface Service<T> {

    String getAll();
    
    String getAllAsDatatablesFormat();

    void updateAll(String body);

    String getSearch(String body);

}
