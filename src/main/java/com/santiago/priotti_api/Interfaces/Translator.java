package com.santiago.priotti_api.Interfaces;

import java.util.List;

public interface Translator<T, Q> {

    List<T> translateList(String body);

    Q translate(String body);

}
