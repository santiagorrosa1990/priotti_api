package com.santiago.priotti_api.User;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.google.inject.Inject;

import java.util.List;

public class UserAuthenticator {

    private UserService service;

    @Inject
    public UserAuthenticator(UserService service) {
        this.service = service;
    }

    public String authenticate(Credentials credentials) {
        List<User> users = service.get(credentials.getUsername());
        if (users.size() == 1 && credentials.matches(users.get(0))) {
            String jwt = createToken(users.get(0));
            verify(jwt);
            return jwt;//users.get(0).getFirstname();
        }
        return "";
    }

    private String createToken(User user){
        try {
            Algorithm algorithm = Algorithm.HMAC256("RE$$TFDLS");
            return JWT.create()
                    .withIssuer("priotti")
                    .withClaim("username", user.getFirstname())
                    .sign(algorithm);
        } catch (JWTCreationException exception){
            return "";
            //Invalid Signing configuration / Couldn't convert Claims.
        }
    }

    private void verify(String token){
        try {
            Algorithm algorithm = Algorithm.HMAC256("RE$$TFDLS");
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer("priotti")
                    .build(); //Reusable verifier instance
            DecodedJWT jwt = verifier.verify(token);
            System.out.println(jwt.getClaim("username").asString());
        } catch (JWTVerificationException exception){
            System.out.println("Invalid token");
        }
    }
}
