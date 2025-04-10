package com.example.movies_api.auth.adapter;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

//Open-Close Principle 3/3 (data steering) [added class]
@Component
public class AuthAdapterRegistry {

    private final Map<String, AuthenticationAdapter> adapterMap = new HashMap<>();

    public AuthAdapterRegistry(List<AuthenticationAdapter> adapters) {
        for (AuthenticationAdapter adapter : adapters) {
            adapterMap.put(adapter.getSupportedContentType().toLowerCase(), adapter);
        }
    }

    public AuthenticationAdapter getAdapter(String contentType) {
        return adapterMap.get(contentType.toLowerCase());
    }
}
