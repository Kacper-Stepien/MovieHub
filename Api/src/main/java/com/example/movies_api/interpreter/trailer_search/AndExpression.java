package com.example.movies_api.interpreter.trailer_search;

import com.example.movies_api.dto.TrailerDto;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Non-terminal expression that combines two expressions with AND logic
 */
public class AndExpression implements TrailerSearchExpression {
    private final TrailerSearchExpression expr1;
    private final TrailerSearchExpression expr2;

    public AndExpression(TrailerSearchExpression expr1, TrailerSearchExpression expr2) {
        this.expr1 = expr1;
        this.expr2 = expr2;
    }

    @Override
    public List<TrailerDto> interpret(TrailerSearchContext context) {
        List<TrailerDto> result1 = expr1.interpret(context);
        List<TrailerDto> result2 = expr2.interpret(context);
        
        return result1.stream()
                .filter(result2::contains)
                .collect(Collectors.toList());
    }
}
