package com.example.movies_api.auth.adapter;

import com.example.movies_api.auth.AuthenticationRequest;
import com.example.movies_api.auth.AuthenticationResponse;
import com.example.movies_api.auth.AuthenticationService;
import org.springframework.stereotype.Component;

@Component
public class JsonAuthenticationAdapter implements AuthenticationAdapter{
    private final AuthenticationService authenticationService;
    public JsonAuthenticationAdapter(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }
    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        return authenticationService.authenticate(request);
    }
}
