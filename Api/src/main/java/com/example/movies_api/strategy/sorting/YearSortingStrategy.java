package com.example.movies_api.strategy.sorting;

import com.example.movies_api.dto.MovieDto;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class YearSortingStrategy implements MovieSortingStrategy {
    @Override
    public List<MovieDto> sort(List<MovieDto> movies) {
        return movies.stream()
                .sorted(Comparator.comparing(MovieDto::getReleaseYear, 
                        Comparator.nullsLast(Comparator.reverseOrder())))
                .collect(Collectors.toList());
    }
}
