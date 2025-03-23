package com.example.demo.security;

import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationProvider;
import org.springframework.security.oauth2.server.resource.authentication.OpaqueTokenAuthenticationProvider;

import java.util.Arrays;

public class HybridAuthenticationManager extends ProviderManager {

    public HybridAuthenticationManager(
            JwtAuthenticationProvider jwtAuthenticationProvider,
            OpaqueTokenAuthenticationProvider opaqueTokenAuthenticationProvider) {
        super(Arrays.asList(jwtAuthenticationProvider, opaqueTokenAuthenticationProvider));
    }
}
