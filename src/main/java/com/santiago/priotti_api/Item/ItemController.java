package com.santiago.priotti_api.Item;

import com.google.gson.Gson;
import com.google.inject.Inject;
import com.santiago.priotti_api.Interfaces.IRequest;
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
        response.type("application/json"); //TODO no deberia usarse nunca esto
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

    public String search(Request request, Response response) {
        response.type("application/json");
        RequestWrapper wrapper = new RequestWrapper(request);
        try {
            ItemRequest itemRequest = new ItemTranslator().translate(wrapper.getBody());
            if (authenticator.authenticate(wrapper.getCredentials())) return itemService.getFullSearch(itemRequest);
            return new Gson().toJson(new StandardResponse(StatusResponse.ERROR, "Authentication failed"));
        } catch (Exception e) {
            e.printStackTrace();
            return new Gson().toJson(new StandardResponse(StatusResponse.ERROR, "Bad ApiRequest"));
        }
    }

    //TODO en esta capa debe ir la autenticación de usuario y la encriptación
}
