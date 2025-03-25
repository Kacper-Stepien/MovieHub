package com.example.movies_api.service;

import com.example.movies_api.interpreter.rating_expression.RatingExpression;
import com.example.movies_api.interpreter.rating_expression.RatingExpressionParser;
import org.springframework.stereotype.Service;

/**
 * Service for evaluating rating expressions using the Interpreter pattern
 */
@Service
public class RatingExpressionService {
    
    /**
     * Evaluate a rating expression in postfix notation
     * Examples: 
     * "4.5 3.2 +" evaluates to 7.7
     * "4.5 3.2 1.7 AVG 3" evaluates to 3.13
     * 
     * @param expression the rating expression to evaluate
     * @return the result of the evaluation
     */
    public double evaluateExpression(String expression) {
        RatingExpressionParser parser = new RatingExpressionParser();
        RatingExpression ratingExpression = parser.parse(expression);
        return ratingExpression.interpret();
    }
}
