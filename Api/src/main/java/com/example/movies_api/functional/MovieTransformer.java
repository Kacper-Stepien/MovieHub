package com.example.movies_api.functional;

import com.example.movies_api.dto.MovieDto;

/**
 * Functional interface for transforming movie objects
 */
@FunctionalInterface
public interface MovieTransformer<R> {
    
    /**
     * Transforms a movie object into another type
     * 
     * @param movie Input movie
     * @return Transformed object
     */
    R apply(MovieDto movie);
}
