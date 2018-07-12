package com.santiago.priotti_api.User;

import com.google.inject.Inject;
import com.santiago.priotti_api.Interfaces.IRequest;

import java.util.List;
import java.util.Map;

public class UserAuthenticator {

    UserService service;

    @Inject
    public UserAuthenticator(UserService service) {
        this.service = service;
    }

    public Boolean authenticate(IRequest request) {
        if (request.hasCredentials()) {
            Map<String, String> credentials = request.getCredentials(); //TODO usar objeto credentials
            return credentials.get("username").equals("srosa") && credentials.get("password").equals("1432");
            /*List<User> users = service.get(credentials.get("username"));
            if(users.size() == 1 && users.get(0).getPassword().equals(credentials.get("password"))){
                return true; //TODO revisar
            }*/
        }
        return false;
    }
}
