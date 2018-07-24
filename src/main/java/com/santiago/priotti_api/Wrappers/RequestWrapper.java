package com.santiago.priotti_api.Wrappers;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.google.gson.Gson;
import com.santiago.priotti_api.User.Credentials;
import spark.Request;

import java.util.Map;
import java.util.Optional;


public class RequestWrapper {

    Request request;
    String body;

    public RequestWrapper(Request request) {
        this.request = request;
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
            Map body = gson.fromJson(request.body(), Map.class);
            body.put("coeficient", coeficient);
            this.body = gson.toJson(body);
        } catch (JWTVerificationException exception){
            return false;
        }
        return true;
    }
}
