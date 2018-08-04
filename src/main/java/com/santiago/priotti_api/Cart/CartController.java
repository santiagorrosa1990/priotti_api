package com.santiago.priotti_api.Cart;

import com.google.gson.Gson;
import com.google.inject.Inject;
import com.santiago.priotti_api.Item.ItemTranslator;
import com.santiago.priotti_api.StandardResponse.StandardResponse;
import com.santiago.priotti_api.StandardResponse.StatusResponse;
import com.santiago.priotti_api.User.UserAuthenticator;
import com.santiago.priotti_api.Wrappers.RequestWrapper;
import spark.Request;
import spark.Response;

public class CartController {

    private final CartService cartService;
    private final UserAuthenticator authenticator; //TODO remove

    @Inject
    public CartController(CartService cartService, UserAuthenticator authenticator) {
        this.cartService = cartService;
        this.authenticator = authenticator;
    }

    public String editCart(Request request, Response response) {
        response.type("application/json");
        RequestWrapper wrapper = new RequestWrapper(request);
        try {
            if (wrapper.validToken()) {
                CartRequest cartRequest = new ItemTranslator().translateCart(wrapper.getFullBody());
                return cartService.cartAddOrRemove(cartRequest);
            }
            response.status(403);
            return new Gson().toJson(new StandardResponse(StatusResponse.ERROR, "Authentication failed"));
        } catch (Exception e) {
            e.printStackTrace();
            response.status(400);
            return new Gson().toJson(new StandardResponse(StatusResponse.ERROR, "Bad Request"));
        }
    }

    public String getCart(Request request, Response response) {
        response.type("application/json");
        RequestWrapper wrapper = new RequestWrapper(request);
        try {
            if (wrapper.validToken()) {
                CartRequest cartRequest = new ItemTranslator().translateCart(wrapper.getFullBody());
                return cartService.getOrder(cartRequest);
            }
            response.status(403);
            return new Gson().toJson(new StandardResponse(StatusResponse.ERROR, "Authentication failed"));
        } catch (Exception e) {
            e.printStackTrace();
            response.status(400);
            return new Gson().toJson(new StandardResponse(StatusResponse.ERROR, "Bad Request"));
        }
    }

    public String getOrderHistory(Request request, Response response) {
        response.type("application/json");
        RequestWrapper wrapper = new RequestWrapper(request);
        try {
            if (wrapper.validToken()) {
                CartRequest cartRequest = new ItemTranslator().translateCart(wrapper.getFullBody());
                return cartService.getOrderHistory(cartRequest);
            }
            response.status(403);
            return new Gson().toJson(new StandardResponse(StatusResponse.ERROR, "Authentication failed"));
        } catch (Exception e) {
            e.printStackTrace();
            response.status(400);
            return new Gson().toJson(new StandardResponse(StatusResponse.ERROR, "Bad Request"));
        }
    }

    public String emailOrder(Request request, Response response) {
        response.type("application/json");
        RequestWrapper wrapper = new RequestWrapper(request);
        try {
            if (wrapper.validToken()) {
                CartRequest cartRequest = new ItemTranslator().translateCart(wrapper.getFullBody());
                if(cartService.sendOrderEmail(cartRequest)){
                    return "Pedido enviado!";
                }
                response.status(403);
                return "Error al enviar pedido";
            }
            response.status(403);
            return "Error de autenticación";
        } catch (Exception e) {
            e.printStackTrace();
            response.status(400);
            return new Gson().toJson(new StandardResponse(StatusResponse.ERROR, "Bad Request"));
        }
    }
}
