package com.example.movies_api.interpreter.trailer_search;

import com.example.movies_api.dto.TrailerDto;
import java.util.List;

/**
 * Expression interface for trailer search - part of Interpreter pattern
 */
public interface TrailerSearchExpression {
    List<TrailerDto> interpret(TrailerSearchContext context);
}
