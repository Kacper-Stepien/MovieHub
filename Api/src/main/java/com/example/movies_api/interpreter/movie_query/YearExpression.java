package com.example.movies_api.interpreter.movie_query;

import com.example.movies_api.dto.MovieDto;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Terminal expression for filtering movies by release year
 */
public class YearExpression implements Expression {
    private final int year;

    public YearExpression(int year) {
        this.year = year;
    }

    @Override
    public List<MovieDto> interpret(Context context) {
        return context.getAllMovies().stream()
                .filter(movie -> movie.getReleaseYear() == year)
                .collect(Collectors.toList());
    }
}
