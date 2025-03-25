package com.example.movies_api.interpreter.trailer_search;

import java.util.Stack;

/**
 * Parser for trailer search query language
 */
public class TrailerSearchParser {

    public TrailerSearchExpression parse(String query) {
        if (query == null || query.trim().isEmpty()) {
            throw new IllegalArgumentException("Query cannot be empty");
        }

        String[] tokens = query.split("\\s+");
        Stack<TrailerSearchExpression> stack = new Stack<>();

        for (int i = 0; i < tokens.length;) {
            String token = tokens[i].toUpperCase();

            switch (token) {
                case "TITLE":
                    if (i + 1 < tokens.length) {
                        stack.push(new TitleExpression(tokens[i + 1]));
                        i += 2;
                    } else {
                        throw new IllegalArgumentException("Expected keyword after TITLE");
                    }
                    break;

                case "DESC":
                case "DESCRIPTION":
                    if (i + 1 < tokens.length) {
                        stack.push(new DescriptionExpression(tokens[i + 1]));
                        i += 2;
                    } else {
                        throw new IllegalArgumentException("Expected keyword after DESCRIPTION");
                    }
                    break;

                case "AND":
                    i++;
                    if (i >= tokens.length) {
                        throw new IllegalArgumentException("Missing right-hand expression after AND");
                    }
                    TrailerSearchExpression leftAnd = stack.pop();
                    TrailerSearchExpression rightAnd = parse(String.join(" ",
                            java.util.Arrays.copyOfRange(tokens, i, tokens.length)));
                    stack.push(new AndExpression(leftAnd, rightAnd));
                    return stack.pop();

                case "OR":
                    i++;
                    if (i >= tokens.length) {
                        throw new IllegalArgumentException("Missing right-hand expression after OR");
                    }
                    TrailerSearchExpression leftOr = stack.pop();
                    TrailerSearchExpression rightOr = parse(String.join(" ",
                            java.util.Arrays.copyOfRange(tokens, i, tokens.length)));
                    stack.push(new OrExpression(leftOr, rightOr));
                    return stack.pop();

                case "NOT":
                    if (stack.isEmpty()) {
                        throw new IllegalArgumentException("No expression to negate");
                    }
                    stack.push(new NotExpression(stack.pop()));
                    i++;
                    break;

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
