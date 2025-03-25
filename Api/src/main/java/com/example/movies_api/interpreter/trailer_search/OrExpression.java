package com.example.movies_api.interpreter.trailer_search;

import com.example.movies_api.dto.TrailerDto;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Non-terminal expression that combines two expressions with OR logic
 */
public class OrExpression implements TrailerSearchExpression {
    private final TrailerSearchExpression expr1;
    private final TrailerSearchExpression expr2;

    public OrExpression(TrailerSearchExpression expr1, TrailerSearchExpression expr2) {
        this.expr1 = expr1;
        this.expr2 = expr2;
    }

    @Override
    public List<TrailerDto> interpret(TrailerSearchContext context) {
        List<TrailerDto> result1 = expr1.interpret(context);
        List<TrailerDto> result2 = expr2.interpret(context);
        
        Set<TrailerDto> resultSet = new HashSet<>(result1);
        resultSet.addAll(result2);
        
        return new ArrayList<>(resultSet);
    }
}
