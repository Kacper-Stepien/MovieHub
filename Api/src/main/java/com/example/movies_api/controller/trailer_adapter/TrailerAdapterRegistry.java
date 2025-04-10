package com.example.movies_api.controller.trailer_adapter;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

//Open-Close Principle 2/3 (data steering) [added class]
@Component
public class TrailerAdapterRegistry {

    private final Map<String, TrailerAdapter> adapterMap = new HashMap<>();

    public TrailerAdapterRegistry(List<TrailerAdapter> adapters) {
        for (TrailerAdapter adapter : adapters) {
            adapterMap.put(adapter.getSupportedContentType(), adapter);
        }
    }

    public TrailerAdapter getAdapter(String contentType) {
        return adapterMap.get(contentType.toLowerCase());
    }
}
