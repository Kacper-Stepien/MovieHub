package com.example.movies_api.interpreter.movie_query;

import com.example.movies_api.dto.MovieDto;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Terminal expression for filtering movies by genre
 */
public class GenreExpression implements Expression {
    private final String genre;

    public GenreExpression(String genre) {
        this.genre = genre;
    }

    @Override
    public List<MovieDto> interpret(Context context) {
        return context.getAllMovies().stream()
                .filter(movie -> movie.getGenre().equalsIgnoreCase(genre))
                .collect(Collectors.toList());
    }
}
