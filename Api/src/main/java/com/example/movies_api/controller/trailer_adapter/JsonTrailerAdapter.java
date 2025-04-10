package com.example.movies_api.controller.trailer_adapter;

import com.example.movies_api.dto.TrailerDto;
import com.example.movies_api.service.TrailerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("jsonTrailerAdapter")
public class JsonTrailerAdapter implements TrailerAdapter {

    private final TrailerService trailerService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public JsonTrailerAdapter(TrailerService trailerService) {
        this.trailerService = trailerService;
    }

    @Override
    public String addTrailer(TrailerDto jsonTrailer) throws Exception {
        trailerService.addTrailer(jsonTrailer);
        return "{\"message\": \"Trailer added successfully\"}";
    }

    @Override
    public List<TrailerDto> getAllTrailers() throws Exception {
        return trailerService.findAllTrailers();
    }

    //Open-Close Principle 2/3 (data steering) [added lines]
    @Override
    public String getSupportedContentType() {
        return "application/json";
    }
}