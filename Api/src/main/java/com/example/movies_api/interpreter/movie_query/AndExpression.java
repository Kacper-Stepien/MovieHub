package com.example.movies_api.interpreter.movie_query;

import com.example.movies_api.dto.MovieDto;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Non-terminal expression that combines two expressions with AND logic
 */
public class AndExpression implements Expression {
    private final Expression expr1;
    private final Expression expr2;

    public AndExpression(Expression expr1, Expression expr2) {
        this.expr1 = expr1;
        this.expr2 = expr2;
    }

    @Override
    public List<MovieDto> interpret(Context context) {
        List<MovieDto> resultExpr1 = expr1.interpret(context);
        List<MovieDto> resultExpr2 = expr2.interpret(context);
        
        // Return only movies that appear in both result sets
        return resultExpr1.stream()
                .filter(resultExpr2::contains)
                .collect(Collectors.toList());
    }
}
