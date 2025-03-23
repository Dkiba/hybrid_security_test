package com.example.demo.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationProvider;
import org.springframework.security.oauth2.server.resource.authentication.OpaqueTokenAuthenticationProvider;
import org.springframework.security.oauth2.server.resource.introspection.OpaqueTokenIntrospector;

@Configuration
public class SecurityBeans {

    @Bean
    public HybridTokenAuthenticationFilter hybridTokenAuthenticationFilter(
            JwtDecoder jwtDecoder,
            OpaqueTokenIntrospector opaqueTokenIntrospector) {
        JwtAuthenticationProvider jwtAuthenticationProvider = new JwtAuthenticationProvider(jwtDecoder);
        OpaqueTokenAuthenticationProvider opaqueTokenAuthenticationProvider =
                new OpaqueTokenAuthenticationProvider(opaqueTokenIntrospector);
        HybridAuthenticationManager hybridAuthenticationManager =
                new HybridAuthenticationManager(jwtAuthenticationProvider, opaqueTokenAuthenticationProvider);
        HybridAuthenticationConverter hybridAuthenticationConverter = new HybridAuthenticationConverter(jwtDecoder);
        return new HybridTokenAuthenticationFilter(hybridAuthenticationManager, hybridAuthenticationConverter);
    }
}