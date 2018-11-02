package com.santiago.priotti_api.Item;

import com.google.gson.Gson;
import com.google.inject.Inject;
import com.santiago.priotti_api.StandardResponse.StandardResponse;
import com.santiago.priotti_api.StandardResponse.StatusResponse;
import com.santiago.priotti_api.User.UserAuthenticator;
import com.santiago.priotti_api.Wrappers.RequestWrapper;
import spark.Request;
import spark.Response;

public class ItemController {

    private final ItemService itemService;
    private final UserAuthenticator authenticator; //TODO remove

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

    public String update(Request request, Response response) {
        response.type("application/json");
        RequestWrapper wrapper = new RequestWrapper(request);
        try {
            if (wrapper.validToken()) {
                return itemService.update(request.body());
            }
            response.status(403);
            return new Gson().toJson(new StandardResponse(StatusResponse.ERROR, "Authentication failed"));
        } catch (Exception e) {
            e.printStackTrace();
            response.status(400);
            return new Gson().toJson(new StandardResponse(StatusResponse.ERROR, "Bad Request"));
        }
    }

    public String full(Request request, Response response) {
        response.type("application/json");
        RequestWrapper wrapper = new RequestWrapper(request);
        try {
            if (wrapper.validToken()) { //TODO ver tell dont ask si aplica acá
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

    public Object getXlsx(Request request, Response response) {
        RequestWrapper wrapper = new RequestWrapper(request);
        try {
            if (wrapper.validToken(request.queryParams("tkn"))) { //TODO porqué hago esto?
                ItemRequest itemRequest = new ItemTranslator().translate(wrapper.getFullBody());
                return itemService.buildXlsx(response, itemRequest);
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
