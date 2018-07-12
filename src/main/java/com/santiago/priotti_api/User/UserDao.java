package com.santiago.priotti_api.User;

import com.santiago.priotti_api.MySql.MySqlConnector;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDao extends MySqlConnector {

    public static final Integer LIMIT = 100;
    public static final String TABLE = "usuario";

    public List<User> get(String username) throws SQLException {
        return select(username);
    }

    private List<User> select(String username) throws SQLException {
        List<User> itemList = new ArrayList<>();
        Statement st = connect();
        ResultSet rs;
        rs = st.executeQuery("SELECT * FROM " + TABLE + " LIMIT " + LIMIT);
        close();
        return new UserInterpreter().interpret(rs);
    }
}
