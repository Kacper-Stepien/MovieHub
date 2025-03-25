package com.example.movies_api.interpreter.movie_query;

import com.example.movies_api.dto.MovieDto;
import java.util.List;

/**
 * Expression interface for movie queries - part of Interpreter pattern
 * Defines how to evaluate expressions in our movie query language
 */
public interface Expression {
    List<MovieDto> interpret(Context context);
}
