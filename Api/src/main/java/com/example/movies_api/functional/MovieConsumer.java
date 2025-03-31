package com.example.movies_api.functional;

import com.example.movies_api.dto.MovieDto;

/**
 * Functional interface for performing operations on movies
 */
@FunctionalInterface
public interface MovieConsumer {
    
    /**
     * Performs an operation on a movie
     * 
     * @param movie Movie to process
     */
    void accept(MovieDto movie);
}
