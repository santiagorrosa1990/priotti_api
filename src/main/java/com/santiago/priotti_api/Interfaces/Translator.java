package com.santiago.priotti_api.Interfaces;

import java.util.List;

public interface Translator<T> {

    List<T> translateList(String body);

    T translateSearchRequest(String body, String p);

}
