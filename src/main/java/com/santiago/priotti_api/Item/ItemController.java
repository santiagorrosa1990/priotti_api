package com.santiago.priotti_api.Item;

import com.google.gson.Gson;
import com.google.inject.Inject;
import com.santiago.priotti_api.Cart.CartRequest;
import com.santiago.priotti_api.StandardResponse.StandardResponse;
import com.santiago.priotti_api.StandardResponse.StatusResponse;
import com.santiago.priotti_api.User.UserAuthenticator;
import com.santiago.priotti_api.Wrappers.RequestWrapper;
import spark.Request;
import spark.Response;

public class ItemController {

    private final ItemService itemService;
    private final UserAuthenticator authenticator;

    @Inject
    public ItemController(ItemService itemService, UserAuthenticator authenticator) {
        this.itemService = itemService;
        this.authenticator = authenticator;
    }


    public String getAll(Request request, Response response) {
        response.type("application/json"); //TODO solo para descarga de la lista
        //if(authenticator.authenticate(request)){
        return itemService.getAll();
        //}
        //return new Gson().toJson(new StandardResponse(StatusResponse.ERROR, "Authentication failed"));
    }

    public String updateAll(Request request, Response response) {
        response.type("application/json");
        itemService.updateAll(request.body());
        return new Gson().toJson(new StandardResponse(StatusResponse.ERROR, "Operation success"));

    }

    public String full(Request request, Response response) {
        response.type("application/json");
        RequestWrapper wrapper = new RequestWrapper(request);
        try {
            if (wrapper.validToken()) {
                ItemRequest itemRequest = new ItemTranslator().translate(wrapper.getFullBody());
                return itemService.fullSearch(itemRequest);
            }
            response.status(403);
            return new Gson().toJson(new StandardResponse(StatusResponse.ERROR, "Authentication failed"));
        } catch (Exception e) {
            e.printStackTrace();
            response.status(400);
            return new Gson().toJson(new StandardResponse(StatusResponse.ERROR, "Bad Request"));
        }
    }

    public String basic(Request request, Response response) {
        response.type("application/json");
        RequestWrapper wrapper = new RequestWrapper(request);
        try {
            ItemRequest itemRequest = new ItemTranslator().translate(wrapper.getBasicBody());
            return itemService.basicSearch(itemRequest);
        } catch (Exception e) {
            e.printStackTrace();
            response.status(400);
            return new Gson().toJson(new StandardResponse(StatusResponse.ERROR, "Bad Request"));
        }
    }

    public String editCart(Request request, Response response) {
        response.type("application/json");
        RequestWrapper wrapper = new RequestWrapper(request);
        try {

            if (wrapper.validToken()) {
                CartRequest cartRequest = new ItemTranslator().translateCart(wrapper.getFullBody());
                return itemService.cartAddOrRemove(cartRequest);
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
                return itemService.getOrder(cartRequest);
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
                return itemService.getOrderHistory(cartRequest);
            }
            response.status(403);
            return new Gson().toJson(new StandardResponse(StatusResponse.ERROR, "Authentication failed"));
        } catch (Exception e) {
            e.printStackTrace();
            response.status(400);
            return new Gson().toJson(new StandardResponse(StatusResponse.ERROR, "Bad Request"));
        }
    }

    public Object emailOrder(Request request, Response response) {
        response.type("application/json");
        RequestWrapper wrapper = new RequestWrapper(request);
        try {
            if (wrapper.validToken()) {
                CartRequest cartRequest = new ItemTranslator().translateCart(wrapper.getFullBody());
                return itemService.sendOrderEmail(cartRequest);
            }
            response.status(403);
            return new Gson().toJson(new StandardResponse(StatusResponse.ERROR, "Authentication failed"));
        } catch (Exception e) {
            e.printStackTrace();
            response.status(400);
            return new Gson().toJson(new StandardResponse(StatusResponse.ERROR, "Bad Request"));
        }
    }


    //TODO en esta capa debe ir la autenticación de usuario y la encriptación
}
