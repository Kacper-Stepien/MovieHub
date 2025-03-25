package com.example.movies_api.interpreter.trailer_search;

import com.example.movies_api.dto.TrailerDto;
import java.util.List;

/**
 * Context class for trailer search interpreter
 */
public class TrailerSearchContext {
    private final List<TrailerDto> allTrailers;

    public TrailerSearchContext(List<TrailerDto> allTrailers) {
        this.allTrailers = allTrailers;
    }

    public List<TrailerDto> getAllTrailers() {
        return allTrailers;
    }
}
