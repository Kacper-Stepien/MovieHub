package com.example.movies_api.auth.adapter;

import com.example.movies_api.auth.AuthenticationRequest;
import com.example.movies_api.auth.AuthenticationResponse;
import com.example.movies_api.auth.AuthenticationService;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class XmlAuthenticationAdapter implements AuthenticationAdapter {
    private final AuthenticationService authenticationService;
    private final XmlMapper xmlMapper;


    public XmlAuthenticationAdapter(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
        this.xmlMapper = new XmlMapper();
    }


    public AuthenticationResponse authenticateXml(String xmlRequest) {
        try {
            AuthenticationRequest request = xmlMapper.readValue(xmlRequest, AuthenticationRequest.class);
            return authenticationService.authenticate(request);
        } catch (IOException e) {
            throw new RuntimeException("Invalid XML format", e);
        }
    }

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        return authenticationService.authenticate(request);
    }
    //Open-Close Principle 3/3 (data steering) [added lines]
    @Override
    public AuthenticationResponse authenticateFromRaw(String rawBody) {
        try {
            AuthenticationRequest request = xmlMapper.readValue(rawBody, AuthenticationRequest.class);
            return authenticate(request);
        } catch (IOException e) {
            throw new RuntimeException("Invalid XML format", e);
        }
    }

    //Open-Close Principle 3/3 (data steering) [added lines]
    @Override
    public String getSupportedContentType() {
        return "application/xml";
    }
}
