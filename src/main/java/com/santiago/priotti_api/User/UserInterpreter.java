package com.santiago.priotti_api.User;

import com.santiago.priotti_api.Interfaces.Interpreter;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
                    .email(Optional.ofNullable(rs.getString("email")).orElse(""))
                    .lastLogin(safeGetAsStringDate(rs.getObject("fechaUltimoLogin", LocalDateTime.class)))
                    .coeficient(rs.getBigDecimal("porcentajeaumento"))
                    .visits(rs.getInt("visitas"))
                    .status(rs.getString("estado"))
                    .admin(getFromInt(rs.getInt("admin")))
                    .build());
        }
        return userList;
    }

    private Boolean getFromInt(Integer tinyInt){
        if(tinyInt == 1 ) return true;
        return false;
    }

    private String safeGetAsStringDate(LocalDateTime time) throws SQLException {
        Optional<LocalDateTime> optional = Optional.ofNullable(time);
        return optional.map(LocalDateTime::toString).orElse("");
    }
}
