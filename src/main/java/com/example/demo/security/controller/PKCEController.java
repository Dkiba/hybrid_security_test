package com.example.demo.security.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/pkce")
public class PKCEController {

    @GetMapping("/generate")
    public Map<String, String> generatePKCE() {
        String codeVerifier = generateCodeVerifier();
        String codeChallenge = generateCodeChallenge(codeVerifier);

        Map<String, String> response = new HashMap<>();
        response.put("code_verifier", codeVerifier);
        response.put("code_challenge", codeChallenge);

        return response;
    }

    private String generateCodeVerifier() {
        SecureRandom random = new SecureRandom();
        byte[] codeVerifier = new byte[32]; // 32 bytes = 256 bits
        random.nextBytes(codeVerifier);
        return base64UrlEncode(codeVerifier);
    }

    private String generateCodeChallenge(String codeVerifier) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(codeVerifier.getBytes(StandardCharsets.US_ASCII));
            return base64UrlEncode(hash);
        } catch (Exception e) {
            throw new RuntimeException("Error generating code challenge", e);
        }
    }

    private String base64UrlEncode(byte[] data) {
        return Base64.getUrlEncoder().withoutPadding().encodeToString(data);
    }
}
