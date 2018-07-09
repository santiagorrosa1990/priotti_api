package com.santiago.priotti_api.User;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Builder
@ToString
@Getter
@EqualsAndHashCode
public class User {

    private String username;
    private String password;
    private String firstname;
    private String lastname;
    private String[] emails;

}
