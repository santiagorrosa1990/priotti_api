package com.santiago.priotti_api.Item;

import com.google.gson.Gson;
import com.google.inject.Inject;
import com.santiago.priotti_api.Interfaces.Controller;
import com.santiago.priotti_api.Interfaces.Service;
import com.santiago.priotti_api.StandardResponse.StandardResponse;
import com.santiago.priotti_api.StandardResponse.StatusResponse;
import com.santiago.priotti_api.User.UserAuthenticator;
import spark.Request;
import spark.Response;

public class ItemController implements Controller {

    private final Service itemService;
    private final UserAuthenticator authenticator;

    @Inject
    public ItemController(Service itemService, UserAuthenticator authenticator) {
        this.itemService = itemService;
        this.authenticator = authenticator;
    }

    @Override
    public String getAll(Request request, Response response) {
        response.type("application/json");
        //if(authenticator.authenticate(request)){
        return itemService.getAllAsDatatablesFormat();
        //}
        //return new Gson().toJson(new StandardResponse(StatusResponse.ERROR, "Authentication failed"));
    }

    @Override
    public String updateAll(Request request, Response response) {
        response.type("application/json");
        itemService.updateAll(request.body());
        return new Gson().toJson(new StandardResponse(StatusResponse.ERROR, "Operation success"));

    }

    @Override
    public String search(Request request, Response response) {
        response.type("application/json");
        try {
            ItemRequest itemRequest = new ItemTranslator().translate(request);
            if (authenticator.authenticate(itemRequest)) {      //TODO implementar interfaz Request con getCredentials();
                return itemService.getSearch(request.body());
            }
            return new Gson().toJson(new StandardResponse(StatusResponse.ERROR, "Authentication failed"));
        } catch (Exception e) {
            return new Gson().toJson(new StandardResponse(StatusResponse.ERROR, "Bad Request"));
        }
    }

    //TODO en esta capa debe ir la autenticación de usuario y la encriptación
}
