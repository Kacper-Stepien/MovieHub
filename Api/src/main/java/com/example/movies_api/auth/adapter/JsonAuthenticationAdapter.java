package com.example.movies_api.auth.adapter;

import com.example.movies_api.auth.AuthenticationRequest;
import com.example.movies_api.auth.AuthenticationResponse;
import com.example.movies_api.auth.AuthenticationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

@Component
public class JsonAuthenticationAdapter implements AuthenticationAdapter{
    private final AuthenticationService authenticationService;
    private final ObjectMapper objectMapper = new ObjectMapper();
    //Open-Close Principle 3/3 (data steering) [added lines]
    public JsonAuthenticationAdapter(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }
    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        return authenticationService.authenticate(request);
    }
    //Open-Close Principle 3/3 (data steering) [added lines]
    @Override
    public AuthenticationResponse authenticateFromRaw(String rawBody) {
        try {
            AuthenticationRequest request = objectMapper.readValue(rawBody, AuthenticationRequest.class);
            return authenticate(request);
        } catch (Exception e) {
            throw new RuntimeException("Invalid JSON format", e);
        }
    }
    //Open-Close Principle 3/3 (data steering) [added lines]
    @Override
    public String getSupportedContentType() {
        return "application/json";
    }
}
