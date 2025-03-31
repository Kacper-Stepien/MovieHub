package com.example.movies_api.functional;

import com.example.movies_api.dto.MovieDto;

/**
 * Functional interface for filtering movies based on specific criteria
 */
@FunctionalInterface
public interface MovieFilter {
    
    /**
     * Checks if a movie meets the specified criteria
     * 
     * @param movie Movie to check
     * @return true if the movie meets the criteria, false otherwise
     */
    boolean test(MovieDto movie);
}
