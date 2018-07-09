package com.santiago.priotti_api.User;

import com.santiago.priotti_api.Interfaces.Dao;
import com.santiago.priotti_api.Item.Item;
import com.santiago.priotti_api.Item.ItemInterpreter;
import com.santiago.priotti_api.MySql.MySqlConnector;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserDao extends MySqlConnector implements Dao<User> {

    public static final int LIMIT = 100;
    public static final String TABLE = "usuario";

    @Override
    public List<User> read() throws SQLException {
        return select(LIMIT);
    }

    @Override
    public void update(List<User> updatedList) throws SQLException {

    }

    @Override
    public void create(User newElement) throws SQLException {

    }

    @Override
    public List<User> search(List keywords) throws SQLException {
        return null;
    }

    private List<User> select(int limit) throws SQLException {
        List<User> itemList = new ArrayList<>();
        Statement st = connect();
        ResultSet rs;
        rs = st.executeQuery("SELECT * FROM " + TABLE + " LIMIT " + limit);
        close();
        return new UserInterpreter().interpret(rs);
    }
}
