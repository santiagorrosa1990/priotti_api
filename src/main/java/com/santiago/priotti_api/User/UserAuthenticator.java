package com.santiago.priotti_api.User;

import com.google.inject.Inject;
import com.santiago.priotti_api.Interfaces.IRequest;

import java.util.List;
import java.util.Map;

public class UserAuthenticator {

    private UserService service;

    @Inject
    public UserAuthenticator(UserService service) {
        this.service = service;
    }

    public Boolean authenticate(Credentials credentials) {
        List<User> users = service.get(credentials.getUsername());
        return users.size() == 1 && credentials.matches(users.get(0));
    }
}
