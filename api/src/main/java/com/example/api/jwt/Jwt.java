package com.example.api.jwt;

import java.util.Date;
import java.util.logging.Logger;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.logging.Level;
import java.util.UUID;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

@Component
public class Jwt {
    private static final Long EXPIRE_DURATION = 8 * 60 * 60 * 1000l;
    private static final Logger LOGGER = Logger.getLogger(Jwt.class.getName());
    private static final String SECRET = "PD46W0FdSDNaQ3tAeENQPUhyTWNQMG94USNwWTtoU1hIMFd43JkWktyWWcpdEpSOChkRTVZdzNNNwLVVoYqNZbj0uaHVAen1QKjByS0k7";
    private static final Algorithm algoritmo = Algorithm.HMAC256(SECRET);

    public String generateJwt(UserDetails userDetails){
        return JWT.create()
                .withIssuer("api") //issuer identifica a parte que criou o token e o assinou
                .withSubject(userDetails.getUsername())
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRE_DURATION))
                .withJWTId(UUID.randomUUID().toString())
                .sign(algoritmo);
    }

    public DecodedJWT decodeJwt(String jwt){
        try {
            JWTVerifier verifier = JWT.require(algoritmo).withIssuer("api").build();
            DecodedJWT decodedJWT = verifier.verify(jwt);
            return decodedJWT;
        } catch (JWTDecodeException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
            throw e;
        } catch(JWTVerificationException e){
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
            throw e;
        }
    }
}
