package com.example.movies_api.interpreter.movie_query;

import com.example.movies_api.dto.MovieDto;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Non-terminal expression that combines two expressions with OR logic
 */
public class OrExpression implements Expression {
    private final Expression expr1;
    private final Expression expr2;

    public OrExpression(Expression expr1, Expression expr2) {
        this.expr1 = expr1;
        this.expr2 = expr2;
    }

    @Override
    public List<MovieDto> interpret(Context context) {
        List<MovieDto> resultExpr1 = expr1.interpret(context);
        List<MovieDto> resultExpr2 = expr2.interpret(context);
        
        // Combine both result sets, removing duplicates
        Set<MovieDto> combinedSet = new HashSet<>(resultExpr1);
        combinedSet.addAll(resultExpr2);
        
        return new ArrayList<>(combinedSet);
    }
}
