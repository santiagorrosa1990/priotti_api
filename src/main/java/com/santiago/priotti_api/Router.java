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

        post("/item/updateall", itemController::updateAll);

        post("/item/full", itemController::full);

        post("/item/update", itemController::update);

        post("/item/basic", itemController::basic);

        post("/item/updcart", itemController::editCart);

        post("/item/getcart", itemController::getCart);

        post("/item/carthist", itemController::getOrderHistory);

        post("/item/emailorder", itemController::emailOrder);

        post("/login", userController::login);

        post("/adminlogin", userController::adminLogin);

        post("/user/list", userController::getList);

        post("/user/update", userController::update);

        get("/ping",(request, response) -> "pong");

        //TODO ver que pasa con usuario: test, test que tira expecion de carrito
        //TODO agregar visitas al login
        //TODO agregar fechas de actualizacion de lista y oferta
        //TODO validar creacion y edicion de clientes
        //TODO coeficiente en lista xlsx
        //TODO Dividir index.js en varios archivos

    }

}
