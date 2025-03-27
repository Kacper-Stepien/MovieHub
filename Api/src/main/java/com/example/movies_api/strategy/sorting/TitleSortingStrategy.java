package com.example.movies_api.strategy.sorting;

import com.example.movies_api.dto.MovieDto;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class TitleSortingStrategy implements MovieSortingStrategy {
    @Override
    public List<MovieDto> sort(List<MovieDto> movies) {
        return movies.stream()
                .sorted(Comparator.comparing(MovieDto::getTitle, 
                        Comparator.nullsLast(String.CASE_INSENSITIVE_ORDER)))
                .collect(Collectors.toList());
    }
}
