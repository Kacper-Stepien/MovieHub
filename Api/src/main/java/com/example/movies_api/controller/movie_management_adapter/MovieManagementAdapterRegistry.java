package com.example.movies_api.controller.movie_management_adapter;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

//Open-Close Principle 1/3 (data steering) [added class]
@Component
public class MovieManagementAdapterRegistry {

    private final Map<String, MovieManagementAdapter> adapterMap = new HashMap<>();

    public MovieManagementAdapterRegistry(List<MovieManagementAdapter> adapters) {
        for (MovieManagementAdapter adapter : adapters) {
            if (adapter instanceof JsonMovieManagementAdapter jsonAdapter) {
                adapterMap.put("application/json", jsonAdapter);
            } else if (adapter instanceof XmlMovieManagementAdapter xmlAdapter) {
                adapterMap.put("application/xml", xmlAdapter);
            }
        }
    }

    public MovieManagementAdapter getAdapter(String contentType) {
        return adapterMap.get(contentType);
    }
}
