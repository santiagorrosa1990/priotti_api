package com.santiago.priotti_api.Interfaces;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public interface Interpreter<T> {

    List<T> interpret(ResultSet rs) throws SQLException;
}
