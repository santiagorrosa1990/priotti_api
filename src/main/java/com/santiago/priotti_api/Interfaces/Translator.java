package com.santiago.priotti_api.Interfaces;

import spark.Request;

import java.util.List;

public interface Translator<T, Q> {

    List<T> translateList(String body);

    Q translate(Request request);

}
