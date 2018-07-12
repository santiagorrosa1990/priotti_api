/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.santiago.priotti_api;

import com.google.inject.Inject;
import com.santiago.priotti_api.Item.ItemController;
import com.santiago.priotti_api.User.UserController;

import static spark.Spark.get;
import static spark.Spark.post;

/**
 *
 * @author santiago
 */
public class Router {

    private final ItemController itemController;
    private final UserController userController;

    @Inject
    public Router(ItemController itemController, UserController userController) {
        this.itemController = itemController;
        this.userController = userController;
    }

    public void launch() {

        get("/item", itemController::getAll);

        post("/item/all", itemController::updateAll);

        post("/item/search", itemController::search);

        post("/login", userController::login);

        get("/ping",(request, response) -> "pong");

        //TODO post("/personas/:nom/:ape/:eda/:dni", (request, response) -> {


    }

}
