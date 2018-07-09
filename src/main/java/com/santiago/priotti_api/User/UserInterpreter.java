package com.santiago.priotti_api.User;

import com.santiago.priotti_api.Interfaces.Interpreter;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserInterpreter implements Interpreter<User> {

    @Override
    public List<User> interpret(ResultSet rs) throws SQLException { //TODO terminar
        List<User> userList = new ArrayList<>();
        while (rs.next()) {
            userList.add(User.builder()
                    .username(rs.getString("idusuario").trim())
                    .password(rs.getString("clave").trim())
                    .firstname(rs.getString("nombre").trim())
                    .lastname(rs.getString("apellido").trim())
                    .build());
        }
        return userList;
    }
}
