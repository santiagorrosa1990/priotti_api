package com.santiago.priotti_api.User;

import com.santiago.priotti_api.Interfaces.IRequest;
import lombok.Builder;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Getter
@Builder
public class UserRequest implements IRequest {

    private Credentials credentials;

    public boolean hasCredentials() {
        return Optional.ofNullable(credentials).isPresent();
    }

    @Override
    public Map<String, String> getPayload() {
        return new HashMap<>();
    }

}
