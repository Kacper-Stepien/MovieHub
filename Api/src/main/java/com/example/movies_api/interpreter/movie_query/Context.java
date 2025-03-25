package com.example.movies_api.interpreter.movie_query;

import com.example.movies_api.dto.MovieDto;
import java.util.List;

/**
 * Context class for movie query interpreter
 * Holds data and state information needed during interpretation
 */
public class Context {
    private final List<MovieDto> allMovies;

    public Context(List<MovieDto> allMovies) {
        this.allMovies = allMovies;
    }

    public List<MovieDto> getAllMovies() {
        return allMovies;
    }
}
