package com.example.movies_api.interpreter.movie_query;

import java.util.Stack;

/**
 * Parser for movie query language
 * Converts query strings into expression trees
 */
public class QueryParser {
    
    public Expression parse(String query) {
        if (query == null || query.trim().isEmpty()) {
            throw new IllegalArgumentException("Query cannot be empty");
        }
        
        String[] tokens = query.split("\\s+");
        Stack<Expression> stack = new Stack<>();
        
        for (int i = 0; i < tokens.length;) {
            String token = tokens[i].toUpperCase();

            switch (token) {
                case "GENRE":
                    if (i + 1 < tokens.length) {
                        stack.push(new GenreExpression(tokens[i + 1]));
                        i += 2;
                    } else {
                        throw new IllegalArgumentException("Missing value for GENRE");
                    }
                    break;
                case "YEAR":
                    if (i + 1 < tokens.length) {
                        try {
                            stack.push(new YearExpression(Integer.parseInt(tokens[i + 1])));
                            i += 2;
                        } catch (NumberFormatException e) {
                            throw new IllegalArgumentException("Invalid value for YEAR: " + tokens[i + 1]);
                        }
                    } else {
                        throw new IllegalArgumentException("Missing value for YEAR");
                    }
                    break;
                case "AND":
                    i++;
                    if (i >= tokens.length) {
                        throw new IllegalArgumentException("Missing right-hand expression after AND");
                    }
                    Expression left = stack.pop();
                    Expression rightAnd = parse(
                            String.join(" ", java.util.Arrays.copyOfRange(tokens, i, tokens.length)));
                    stack.push(new AndExpression(left, rightAnd));
                    return stack.pop(); // kończymy, bo resztę zjadł rekurencyjny `parse`
                case "OR":
                    i++;
                    if (i >= tokens.length) {
                        throw new IllegalArgumentException("Missing right-hand expression after OR");
                    }
                    left = stack.pop();
                    Expression rightOr = parse(
                            String.join(" ", java.util.Arrays.copyOfRange(tokens, i, tokens.length)));
                    stack.push(new OrExpression(left, rightOr));
                    return stack.pop();
                default:
                    throw new IllegalArgumentException("Unknown token: " + token);
            }
        }
        
        if (stack.size() != 1) {
            throw new IllegalArgumentException("Invalid query syntax");
        }
        
        return stack.pop();
    }
}
