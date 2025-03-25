package com.example.movies_api.interpreter.rating_expression;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.StringTokenizer;

/**
 * Parser for rating expression language
 */
public class RatingExpressionParser {
    
    public RatingExpression parse(String expression) {
        if (expression == null || expression.trim().isEmpty()) {
            throw new IllegalArgumentException("Expression cannot be empty");
        }
        
        Stack<RatingExpression> stack = new Stack<>();
        StringTokenizer tokenizer = new StringTokenizer(expression, " ", false);
        
        while (tokenizer.hasMoreTokens()) {
            String token = tokenizer.nextToken().trim();
            
            if (token.equals("ADD")) {
                if (stack.size() < 2) {
                    throw new IllegalArgumentException("Not enough operands for addition");
                }
                RatingExpression right = stack.pop();
                RatingExpression left = stack.pop();
                stack.push(new AddExpression(left, right));
            } else if (token.equals("SUB")) {
                if (stack.size() < 2) {
                    throw new IllegalArgumentException("Not enough operands for subtraction");
                }
                RatingExpression right = stack.pop();
                RatingExpression left = stack.pop();
                stack.push(new SubtractExpression(left, right));
            } else if (token.equalsIgnoreCase("AVG")) {
                if (stack.isEmpty()) {
                    throw new IllegalArgumentException("No expressions to average");
                }
                
                // Check if the next token specifies the number of expressions to average
                if (tokenizer.hasMoreTokens()) {
                    String countToken = tokenizer.nextToken();
                    try {
                        int count = Integer.parseInt(countToken);
                        if (count > stack.size()) {
                            throw new IllegalArgumentException("Not enough expressions to average");
                        }

                        List<RatingExpression> expressions = new ArrayList<>();
                        for (int i = 0; i < count; i++) {
                            expressions.add(0, stack.pop()); // zachowaj kolejność
                        }

                        stack.push(new AverageExpression(expressions));
                    } catch (NumberFormatException e) {
                        throw new IllegalArgumentException("Expected number after AVG");
                    }
                } else {
                    // Domyślnie: użyj wszystkich wyrażeń ze stosu
                    List<RatingExpression> expressions = new ArrayList<>(stack);
                    stack.clear();
                    stack.push(new AverageExpression(expressions));
                }
            } else {
                try {
                    stack.push(new NumberExpression(Double.parseDouble(token)));
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException("Invalid token: " + token);
                }
            }
        }
        
        if (stack.size() != 1) {
            throw new IllegalArgumentException("Invalid expression syntax");
        }
        
        return stack.pop();
    }
}
