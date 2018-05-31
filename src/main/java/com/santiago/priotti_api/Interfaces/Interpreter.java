package com.santiago.priotti_api.Interfaces;

import java.util.List;

public interface Interpreter<T> {

    public List<T> interpret(String rawBody);
}
