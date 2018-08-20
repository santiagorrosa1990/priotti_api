package com.santiago.priotti_api.User;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.google.inject.Inject;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

public class UserAuthenticator {

    private UserService service;

    @Inject
    public UserAuthenticator(UserService service) {
        this.service = service;
    }

    public String buildToken(Credentials credentials) {
        List<User> users = service.get(credentials.getUsername());
        if (users.size() == 1 && credentials.matches(users.get(0))) {
            service.addVisit();
            return Optional.ofNullable(createToken(users.get(0))).orElse("");
        }
        return "";
    }

    public String buildAdminToken(Credentials credentials) {
        List<User> users = service.get(credentials.getUsername());
        if (users.size() == 1 && credentials.matches(users.get(0)) && users.get(0).getAdmin()) return Optional.ofNullable(createToken(users.get(0))).orElse("");
        return "";
    }

    private String createToken(User user){
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
            String formatDateTime = LocalDateTime.now().format(formatter);
            Algorithm algorithm = Algorithm.HMAC256("RE$$TFDLS");
            return JWT.create()
                    .withIssuer("priotti")
                    .withClaim("date", formatDateTime)
                    .withClaim("username", user.getName())
                    .withClaim("id", user.getId())
                    .withClaim("co", user.getCoeficient().toString())
                    .sign(algorithm);
        } catch (JWTCreationException exception){
            return null;
        }
    }

    public Boolean verify(String token){
        try {
            Algorithm algorithm = Algorithm.HMAC256("RE$$TFDLS");
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer("priotti")
                    .build(); //Reusable verifier instance
            verifier.verify(token);
            return true;
        } catch (JWTVerificationException exception){
            System.out.println("Invalid token");
            return false;
        }
    }


}
