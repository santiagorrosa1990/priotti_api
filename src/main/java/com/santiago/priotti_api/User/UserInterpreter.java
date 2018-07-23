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
                    .id(rs.getInt("id"))
                    .name(rs.getString("nombre").trim())
                    .number(rs.getString("numero").trim())
                    .cuit(rs.getString("cuit").trim())
                    .coeficient(rs.getBigDecimal("porcentajeaumento"))
                    .build());
        }
        return userList;
    }
}
