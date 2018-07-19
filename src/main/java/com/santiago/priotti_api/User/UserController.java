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

    @Inject
    public UserController(UserAuthenticator authenticator) {
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
}
