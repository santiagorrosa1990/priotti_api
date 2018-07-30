package com.santiago.priotti_api.User;

import com.santiago.priotti_api.MySql.MySqlConnector;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

public class UserDao extends MySqlConnector {

    public static final Integer LIMIT = 100;
    public static final String TABLE = "clientes"; //TODO eliminar tabla usuario y renombrar la de clientes

    public List<User> get(String username) throws SQLException {
        String query = "SELECT * FROM " + TABLE + " WHERE numero = '" + username + "'";
        Statement st = connect();
        ResultSet rs;
        rs = st.executeQuery(query);
        List<User> userList = new UserInterpreter().interpret(rs);
        close();
        return userList;
    }

    public List<User> getAll() throws SQLException {
        String query = "SELECT * FROM " + TABLE;
        Statement st = connect();
        ResultSet rs;
        rs = st.executeQuery(query);
        List<User> userList = new UserInterpreter().interpret(rs);
        close();
        return userList;
    }

    public void update(User user) throws SQLException {
        Statement st = connect();
        if (Optional.ofNullable(user.getId()).isPresent()) {
            String query = "UPDATE clientes set nombre = '%s', numero = '%s', cuit = '%s', porcentajeaumento = '%s', email = '%s' WHERE id  = %d";
            query = String.format(query, user.getName(), user.getNumber(), user.getCuit(), user.getCoeficient(), user.getEmail(), user.getId());
            st.executeUpdate(query);
        } else {
            String query = "INSERT INTO clientes(numero, cuit, nombre, porcentajeaumento, email, fechaAlta) VALUES ('%s', '%s', '%s', '%s', '%s', now())";
            query = String.format(query, user.getNumber(), user.getCuit(), user.getName(), user.getCoeficient(), user.getEmail());
            st.executeUpdate(query);
        }
        close();
    }
}
