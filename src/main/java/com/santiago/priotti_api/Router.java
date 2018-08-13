/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.santiago.priotti_api;

import com.google.inject.Inject;
import com.santiago.priotti_api.Cart.CartController;
import com.santiago.priotti_api.Item.ItemController;
import com.santiago.priotti_api.User.UserController;

import static spark.Spark.before;
import static spark.Spark.get;
import static spark.Spark.post;

/**
 *
 * @author santiago
 */
public class Router {

    private final ItemController itemController;
    private final UserController userController;
    private final CartController cartController;

    @Inject
    public Router(ItemController itemController, UserController userController, CartController cartController) {
        this.itemController = itemController;
        this.userController = userController;
        this.cartController = cartController;
    }

    public void launch() {

        before();

        get("/item", itemController::getAll);

        post("/item/updateall", itemController::updateAll);

        post("/item/full", itemController::full);

        post("/item/update", itemController::update);

        post("/item/basic", itemController::basic);

        post("/item/updcart", cartController::editCart);

        post("/item/getcart", cartController::getCart);

        post("/item/carthist", cartController::getOrderHistory);

        post("/item/emailorder", cartController::emailOrder);

        post("/login", userController::login);

        post("/adminlogin", userController::adminLogin);

        post("/user/list", userController::getList);

        post("/user/update", userController::update);

        get("/ping",(request, response) -> "pong");

        get("/xls", itemController::getXlsx);

        //TODO agregar visitas al login
        //TODO agregar fechas de actualizacion de lista y oferta
        //TODO validar creacion y edicion de clientes
        //TODO dividir index.js en varios archivos
        //TODO revisar conexion de base de datos, que no quede nada abierto
        //TODO al borrar cantidad de un item, se borra del carrito
        //TODO ver ordenamiento del render del datatables
        //TODO se recarga el item en 0 cuando se vuelve a clickear un producto ya existente

    }

}
