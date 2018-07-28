package com.santiago.priotti_api.User;

import com.google.gson.Gson;
import com.google.inject.Inject;
import com.santiago.priotti_api.StandardResponse.StandardResponse;
import com.santiago.priotti_api.StandardResponse.StatusResponse;

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

    public String getAll() {
        try {
            List<User> userList = dao.getAll();
            return new UserPresenter().fullTable(userList);
        } catch (SQLException ex) {
            ex.printStackTrace();
            return new Gson().toJson(new StandardResponse(StatusResponse.ERROR, "Error: " + ex.getMessage()));
        }
    }
}
