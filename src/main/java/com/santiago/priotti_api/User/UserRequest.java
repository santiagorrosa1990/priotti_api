package com.santiago.priotti_api.User;

import com.google.gson.annotations.SerializedName;
import com.santiago.priotti_api.Interfaces.IRequest;
import lombok.Builder;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Builder
public class UserRequest implements IRequest {

    @SerializedName("username")
    private String username;
    @SerializedName("password")
    private String password;

    public boolean hasCredentials() {
        return !username.isEmpty() && !password.isEmpty();
    }

    @Override
    public Map<String, String> getCredentials() {
        Map<String, String> credentials = new HashMap<>();
        credentials.put("username", username);
        credentials.put("password", password); //TODO devolver objeto credentials
        return credentials;
    }

    @Override
    public Map<String, String> getPayload() {
        return new HashMap<>();
    }

}
