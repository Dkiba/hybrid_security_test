package com.example.demo.security;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.security.oauth2.server.resource.authentication.BearerTokenAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.web.authentication.AuthenticationConverter;

public class HybridAuthenticationConverter implements AuthenticationConverter {

    private final JwtDecoder jwtDecoder;

    public HybridAuthenticationConverter(JwtDecoder jwtDecoder) {
        this.jwtDecoder = jwtDecoder;
    }

    @Override
    public Authentication convert(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7);
            System.out.println("TOKEN DADEQ&&&&&&&&&&&&&&&&&&&&: "+token);
            if (isJwt(token)) {
                try {
                    System.out.println("jwt before =======================>: ");
                    Jwt jwt = jwtDecoder.decode(token);
                    System.out.println("jwt after =======================>: "+jwt.toString());
                    return new JwtAuthenticationToken(jwt);
                } catch (JwtException e) {
                    throw new RuntimeException("Invalid JWT: "+e);
                }
            } else {
                return new BearerTokenAuthenticationToken(token);
            }
        }
        return null;
    }

    private boolean isJwt(String token) {
        return token.chars().filter(ch -> ch == '.').count() == 2;
    }
}
