package com.example.movies_api.interpreter.trailer_search;

import com.example.movies_api.dto.TrailerDto;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Non-terminal expression that negates another expression
 */
public class NotExpression implements TrailerSearchExpression {
    private final TrailerSearchExpression expression;

    public NotExpression(TrailerSearchExpression expression) {
        this.expression = expression;
    }

    @Override
    public List<TrailerDto> interpret(TrailerSearchContext context) {
        List<TrailerDto> result = expression.interpret(context);
        
        return context.getAllTrailers().stream()
                .filter(trailer -> !result.contains(trailer))
                .collect(Collectors.toList());
    }
}
