package com.example.demo.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationProvider;
import org.springframework.security.oauth2.server.resource.authentication.OpaqueTokenAuthenticationProvider;
import org.springframework.security.web.authentication.AuthenticationFilter;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class HybridTokenAuthenticationFilter extends AuthenticationFilter {

    public HybridTokenAuthenticationFilter(
            HybridAuthenticationManager hybridAuthenticationManager,
            HybridAuthenticationConverter hybridAuthenticationConverter) {
        super(hybridAuthenticationManager, hybridAuthenticationConverter);
    }
}