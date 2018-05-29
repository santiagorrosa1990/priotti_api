/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.santiago.priotti_api;

import com.google.inject.Inject;
import com.santiago.priotti_api.Interfaces.Controller;
import static spark.Spark.*;

/**
 *
 * @author santiago
 */
public class Router {

    private final Controller itemController;

    @Inject
    public Router(Controller itemController) {
        this.itemController = itemController;
    }

    public void launch() {

        get("/item", (request, response) -> {
            return itemController.getAll(request, response);
        });

        post("/prueba", (request, response) -> {
            System.out.println(request.body());
            return "Ok";
        });

        //        post("/personas/:nom/:ape/:eda/:dni", (request, response) -> {
//            return PersonaController.addPersona(request, response);
//        });
//
//        delete("/personas/:dni", (request, response) -> {
//            return PersonaController.deletePersona(request, response);
//        });
    }

}
