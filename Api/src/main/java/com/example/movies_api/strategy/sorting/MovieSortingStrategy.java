package com.example.movies_api.strategy.sorting;

import com.example.movies_api.dto.MovieDto;
import java.util.List;

/**
 * Strategy interface for different movie sorting algorithms
 */
public interface MovieSortingStrategy {
    List<MovieDto> sort(List<MovieDto> movies);
}
