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

        post("/item/full", itemController::full);

        post("/item/basic", itemController::basic);

        post("/item/updcart", itemController::editCart);

        post("/item/getcart", itemController::getCart);

        post("/item/carthist", itemController::getOrderHistory);

        post("/item/emailorder", itemController::emailOrder);

        post("/login", userController::login);

        post("/adminlogin", userController::adminLogin);

        get("/ping",(request, response) -> "pong");

        //TODO post("/personas/:nom/:ape/:eda/:dni", (request, response) -> {
        //TODO agregar coeficiente a los precios
        //TODO meter toda la info del cliente en el token para que el usuario no la pueda ver
        //TODO en el localstorage y que se decodifique cuando se deba usar algo Ej: coeficiente

    }

}
