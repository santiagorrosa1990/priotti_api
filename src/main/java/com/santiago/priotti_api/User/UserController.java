package com.santiago.priotti_api.User;

import com.google.gson.Gson;
import com.google.inject.Inject;
import com.santiago.priotti_api.Interfaces.IRequest;
import com.santiago.priotti_api.StandardResponse.StandardResponse;
import com.santiago.priotti_api.StandardResponse.StatusResponse;
import spark.Request;
import spark.Response;

public class UserController {

    UserAuthenticator authenticator;

    @Inject
    public UserController(UserAuthenticator authenticator) {
        this.authenticator = authenticator;
    }

    public String login(Request request, Response response) {
        response.type("application/json");
        IRequest userRequest = new UserTranslator().translate(request);
        if(authenticator.authenticate(userRequest)){
            return new Gson().toJson(new StandardResponse(StatusResponse.SUCCESS, "Login ok"));
        }
        return new Gson().toJson(new StandardResponse(StatusResponse.ERROR, "Login error"));
    }
}
