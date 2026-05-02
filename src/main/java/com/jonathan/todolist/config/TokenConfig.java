package com.jonathan.todolist.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.jonathan.todolist.model.UserModel;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Optional;

@Component
public class TokenConfig {

    private String secret = "secret"; //Geralmente essa senha vai na variável de ambiente

    Algorithm algorithm = Algorithm.HMAC256(secret);

    public String generateToken(UserModel user){
        return JWT.create()
                .withSubject(user.getLogin())
                .withExpiresAt(Instant.now().plusSeconds(86400))
                .withIssuedAt(Instant.now())
                .sign(algorithm);
    }

    public Optional<JWTUserData> validateToken(String token){

        Algorithm algorithm = Algorithm.HMAC256(secret);

        DecodedJWT decode = JWT.require(algorithm).build().verify(token);

        return Optional.of(JWTUserData.builder().login(decode.getSubject()).build());
    }
}
