package com.santiago.priotti_api.User;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Credentials {

    private String username;
    private String password;

    public Boolean matches(User user) {
        return this.username.equals(user.getNumber()) && this.password.equals(user.getCuit());
    }

}
