package com.santiago.priotti_api.User;

import com.santiago.priotti_api.MySql.MySqlConnector;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class UserDao extends MySqlConnector {

    public static final Integer LIMIT = 100;
    public static final String TABLE = "clientes"; //TODO eliminar tabla usuario y renombrar la de clientes

    public List<User> get(String username) throws SQLException {
        String query = "SELECT * FROM "+TABLE+" WHERE numero = '"+username+"'";
        Statement st = connect();
        ResultSet rs;
        rs = st.executeQuery(query);
        List<User> userList = new UserInterpreter().interpret(rs);
        close();
        return  userList;
    }

    public List<User> getAll() throws SQLException {
        String query = "SELECT * FROM "+TABLE;
        Statement st = connect();
        ResultSet rs;
        rs = st.executeQuery(query);
        List<User> userList = new UserInterpreter().interpret(rs);
        close();
        return  userList;
    }
}
