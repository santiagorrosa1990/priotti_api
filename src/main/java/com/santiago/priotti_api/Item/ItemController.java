package com.santiago.priotti_api.Item;

import com.google.inject.Inject;
import com.santiago.priotti_api.Interfaces.Controller;
import com.santiago.priotti_api.Interfaces.Service;
import spark.Request;
import spark.Response;

public class ItemController implements Controller {

    private final Service itemService;


    @Inject
    public ItemController(Service itemService) {
        this.itemService = itemService;
    }

    @Override
    public String getAll(Request request, Response response) {
        response.type("application/json");
        return itemService.getAllAsDatatablesFormat();
    }

    @Override
    public void updateAll(Request request, Response response) {
        itemService.update(request.body());
    }

}
