package com.example.movies_api.interpreter.rating_expression;

/**
 * Terminal expression for a number in rating calculations
 */
public class NumberExpression implements RatingExpression {
    private final double number;

    public NumberExpression(double number) {
        this.number = number;
    }

    @Override
    public double interpret() {
        return number;
    }
}
