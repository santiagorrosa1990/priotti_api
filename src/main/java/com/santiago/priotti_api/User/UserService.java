package com.santiago.priotti_api.User;

import com.google.inject.Inject;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserService {

    UserDao dao;

    @Inject
    public UserService(UserDao dao) {
        this.dao = dao;
    }

    public List<User> get(String username){
        try {
            return dao.get(username);
        } catch (SQLException ex) {
            ex.printStackTrace();
            return new ArrayList<>(); //TODO revisar
        }
    }
}
