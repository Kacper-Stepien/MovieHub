package com.example.movies_api.interpreter.rating_expression;

import java.util.List;

/**
 * Non-terminal expression for averaging multiple rating expressions
 */
public class AverageExpression implements RatingExpression {
    private final List<RatingExpression> expressions;

    public AverageExpression(List<RatingExpression> expressions) {
        this.expressions = expressions;
    }

    @Override
    public double interpret() {
        if (expressions.isEmpty()) {
            return 0;
        }
        
        double sum = 0;
        for (RatingExpression expression : expressions) {
            sum += expression.interpret();
        }
        
        return sum / expressions.size();
    }
}
