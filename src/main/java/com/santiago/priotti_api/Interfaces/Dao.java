/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.santiago.priotti_api.Interfaces;

import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author santiago
 * @param <T>
 */
public interface Dao<T> {
    
    
    List<T> get() throws SQLException;

    void update(List<T> upToDateList) throws SQLException;
    
}
