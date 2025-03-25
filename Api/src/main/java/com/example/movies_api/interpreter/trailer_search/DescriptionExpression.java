package com.example.movies_api.interpreter.trailer_search;

import com.example.movies_api.dto.TrailerDto;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Terminal expression for searching trailers by description
 */
public class DescriptionExpression implements TrailerSearchExpression {
    private final String keyword;

    public DescriptionExpression(String keyword) {
        this.keyword = keyword.toLowerCase();
    }

    @Override
    public List<TrailerDto> interpret(TrailerSearchContext context) {
        return context.getAllTrailers().stream()
                .filter(trailer -> trailer.getDescription() != null &&
                        trailer.getDescription().toLowerCase().contains(keyword))
                .collect(Collectors.toList());
    }
}
