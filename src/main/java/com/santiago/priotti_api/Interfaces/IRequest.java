package com.santiago.priotti_api.Interfaces;

import com.santiago.priotti_api.User.Credentials;

import java.util.Map;

public interface IRequest {

    boolean hasCredentials();

    Credentials getCredentials();

    Map<String, String> getPayload();

}
