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
public interface Controller {
    
    String getAll(spark.Request request, spark.Response response);

    String updateAll(spark.Request request, spark.Response response);

    String search(spark.Request request, spark.Response response);
    
}
