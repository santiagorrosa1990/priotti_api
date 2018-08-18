package com.santiago.priotti_api.User;

import com.google.gson.Gson;
import com.google.inject.Inject;
import com.santiago.priotti_api.StandardResponse.StandardResponse;
import com.santiago.priotti_api.StandardResponse.StatusResponse;
import com.santiago.priotti_api.Wrappers.RequestWrapper;
import spark.Request;
import spark.Response;

public class UserController {

    UserAuthenticator authenticator;
    UserService userService;

    @Inject
    public UserController(UserAuthenticator authenticator, UserService userService) {
        this.userService = userService;
        this.authenticator = authenticator;
    }

    public String login(Request request, Response response) {
        try{
            RequestWrapper wrapper = new RequestWrapper(request);
            response.type("application/json");
            String token = authenticator.buildToken(wrapper.getCredentials());
            if(token.isEmpty()){
                response.status(403);
                return new Gson().toJson(new StandardResponse(StatusResponse.ERROR, "Invalid credentials"));
            }
            response.type("text");
            return token; //TODO ver tipo de respuesta
        }catch(Exception e){
            response.status(400);
            return new Gson().toJson(new StandardResponse(StatusResponse.ERROR, "Bad request: "+e.getMessage()));
        }
    }

    public String adminLogin(Request request, Response response) { //TODO SEGUIR
        try{
            RequestWrapper wrapper = new RequestWrapper(request);
            response.type("application/json");
            String token = authenticator.buildAdminToken(wrapper.getCredentials());
            if(token.isEmpty()){
                response.status(403);
                return new Gson().toJson(new StandardResponse(StatusResponse.ERROR, "Invalid credentials"));
            }
            response.type("text");
            return token; //TODO ver tipo de respuesta
        }catch(Exception e){
            response.status(400);
            return new Gson().toJson(new StandardResponse(StatusResponse.ERROR, "Bad request: "+e.getMessage()));
        }
    }

    public String getList(Request request, Response response) {
        response.type("application/json");
        RequestWrapper wrapper = new RequestWrapper(request);
        try {
            if (wrapper.validToken()) {
                return userService.getAll();
            }
            response.status(403);
            return new Gson().toJson(new StandardResponse(StatusResponse.ERROR, "Authentication failed"));
        } catch (Exception e) {
            e.printStackTrace();
            response.status(400);
            return new Gson().toJson(new StandardResponse(StatusResponse.ERROR, "Bad Request"));
        }
    }

    public String update(Request request, Response response) {
        response.type("application/json");
        RequestWrapper wrapper = new RequestWrapper(request);
        try {
            if (wrapper.validToken()) {
                return userService.update(request.body());
            }
            response.status(403);
            return new Gson().toJson(new StandardResponse(StatusResponse.ERROR, "Authentication failed"));
        } catch (Exception e) {
            e.printStackTrace();
            response.status(400);
            return new Gson().toJson(new StandardResponse(StatusResponse.ERROR, "Bad Request"));
        }
    }
}
