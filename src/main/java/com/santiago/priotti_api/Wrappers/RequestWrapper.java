package com.santiago.priotti_api.Wrappers;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.google.gson.Gson;
import com.santiago.priotti_api.User.Credentials;
import spark.Request;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


public class RequestWrapper {

    Request request;
    String body;
    String token;

    public RequestWrapper(Request request) {
        this.request = request;
    }

    public RequestWrapper(){}

    public RequestWrapper withToken(String token){
        this.token = token;
        return this;
    }

    public Credentials getCredentials(){
        Map requestMap = new Gson().fromJson(this.request.body(), Map.class);
        return Credentials.builder()
                .username((String) ((Map)requestMap.get("credentials")).get("username")) //TODO ver este casteo
                .password((String) ((Map)requestMap.get("credentials")).get("password"))
                .build();
    }

    public String getToken(){
        Map requestMap = new Gson().fromJson(this.request.body(), Map.class);
        return Optional.ofNullable((String)requestMap.get("token")).orElse("");
    }

    public String getFullBody(){
        return this.body;
    }

    public String getBasicBody(){
        return request.body();
    }

    public Boolean validToken() {
        Gson gson = new Gson();
        try {
            Algorithm algorithm = Algorithm.HMAC256("RE$$TFDLS");
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer("priotti")
                    .build();
            String coeficient = verifier.verify(getToken()).getClaim("co").asString();
            String tokenDate = verifier.verify(getToken()).getClaim("date").asString();
            Map body = gson.fromJson(request.body(), Map.class);
            body.put("coeficient", coeficient); //@Smell ver esto
            this.body = gson.toJson(body);
            if(expired(tokenDate)) return false;
        } catch (JWTVerificationException exception){
            return false;
        }
        return true;
    }

    public Boolean validToken(String token) {
        Gson gson = new Gson();
        try {
            Algorithm algorithm = Algorithm.HMAC256("RE$$TFDLS");
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer("priotti")
                    .build();
            String coeficient = verifier.verify(token).getClaim("co").asString();
            String tokenDate = verifier.verify(getToken()).getClaim("date").asString();
            Map body = Optional.ofNullable(gson.fromJson(request.body(), Map.class)).orElse(new HashMap());
            body.put("coeficient", coeficient); //@Smell ver esto
            this.body = gson.toJson(body);
            if(expired(tokenDate)) return false;
        } catch (JWTVerificationException exception){
            return false;
        }
        return true;
    }

    private boolean expired(String tokenDate){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        LocalDateTime from = LocalDateTime.parse(tokenDate, formatter);
        Long minutes = from.until(LocalDateTime.now(Clock.systemUTC()).plusHours(-3L), ChronoUnit.MINUTES);
        System.out.println("Minutes left: "+ (120L - minutes));
        return !(minutes < 120L);
    }

}
