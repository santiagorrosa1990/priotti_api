package com.santiago.priotti_api.Interfaces;

import java.util.Map;

public interface IRequest {

    boolean hasCredentials();

    Map<String, String> getCredentials(); //TODO hacer un objeto credentials

    Map<String, String> getPayload();

}
