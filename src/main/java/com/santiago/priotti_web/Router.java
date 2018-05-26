/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.priotti_web;

import com.google.inject.Inject;
import com.mycompany.mavenproject1.Interfaces.Controller;
import static spark.Spark.get;

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

        //        post("/personas/:nom/:ape/:eda/:dni", (request, response) -> {
//            return PersonaController.addPersona(request, response);
//        });
//
//        delete("/personas/:dni", (request, response) -> {
//            return PersonaController.deletePersona(request, response);
//        });
    }

}
