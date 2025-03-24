package com.example.demo.security;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.security.oauth2.core.DefaultOAuth2AuthenticatedPrincipal;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.security.oauth2.server.resource.introspection.BadOpaqueTokenException;
import org.springframework.security.oauth2.server.resource.introspection.OpaqueTokenIntrospector;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.Map;


@Component
public class CustomOpaqueTokenIntrospector implements OpaqueTokenIntrospector {

    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public OAuth2AuthenticatedPrincipal introspect(String token) {
        // Call the authorization server to validate the opaque token
        String introspectionUrl = "http://localhost:8080/oauth2/introspect"; // Introspection endpoint

        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth("123", "12345678"); // Replace with your client credentials
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("token", token);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);
        ResponseEntity<Map<String, Object>> response = restTemplate.exchange(
                introspectionUrl,
                HttpMethod.POST,
                request,
                new ParameterizedTypeReference<Map<String, Object>>() {}
        );

        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            Map<String, Object> claims = response.getBody();
            return new DefaultOAuth2AuthenticatedPrincipal(claims, Collections.emptyList());
        } else {
            throw new BadOpaqueTokenException("Invalid token");
        }
    }
}