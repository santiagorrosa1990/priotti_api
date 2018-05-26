package com.santiago.priotti_web.Item;

import com.google.inject.Inject;
import com.santiago.priotti_web.Interfaces.Controller;
import com.santiago.priotti_web.Interfaces.Service;
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
        return itemService.getAll();
    }

//    public static String addPersona(spark.Request request, spark.Response response) throws IOException {
//        response.type("application/json");
//        String nom = request.params("nom");
//        String ape = request.params("ape");
//        int eda = Integer.parseInt(request.params("eda"));
//        int dni = Integer.parseInt(request.params("dni"));
//        boolean res = PersonaService.addPersona(new Persona(nom, ape, eda, dni));
//        if (res == true) {
//            response.redirect("http://localhost:4567/personas");
//            return new Gson().toJson(new StandardResponse(StatusResponse.SUCCESS));
//        } else {
//            return new Gson().toJson(new StandardResponse(StatusResponse.ERROR, "Ya existe DNI"));
//        }
//
//    }
//
//    public static String deletePersona(spark.Request request, spark.Response response) throws IOException {
//        boolean deleted = PersonaService.deletePersona(Integer.parseInt(request.params("dni")));
//        if (deleted) {
//            return new Gson().toJson(new StandardResponse(StatusResponse.SUCCESS));
//        }
//        return new Gson().toJson(new StandardResponse(StatusResponse.ERROR, "No existe ese DNI"));
//    }
}
