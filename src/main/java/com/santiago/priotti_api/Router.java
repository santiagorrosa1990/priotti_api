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

        get("/item", itemController::getAll);

        post("/item/all", itemController::updateAll);

        post("/item/search", itemController::search);

        get("/ping",(request, response) -> "pong");

        //        post("/personas/:nom/:ape/:eda/:dni", (request, response) -> {
//            return PersonaController.addPersona(request, response);
//        });


    }

}
