package com.example.movies_api.interpreter.rating_expression;

/**
 * Non-terminal expression for addition in rating calculations
 */
public class AddExpression implements RatingExpression {
    private final RatingExpression leftExpression;
    private final RatingExpression rightExpression;

    public AddExpression(RatingExpression leftExpression, RatingExpression rightExpression) {
        this.leftExpression = leftExpression;
        this.rightExpression = rightExpression;
    }

    @Override
    public double interpret() {
        return leftExpression.interpret() + rightExpression.interpret();
    }
}
