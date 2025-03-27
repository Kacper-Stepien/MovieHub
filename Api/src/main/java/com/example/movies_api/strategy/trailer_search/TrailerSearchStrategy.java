package com.example.movies_api.strategy.trailer_search;

import com.example.movies_api.dto.TrailerDto;
import java.util.List;

/**
 * Strategy interface for different trailer search algorithms
 */
public interface TrailerSearchStrategy {
    List<TrailerDto> search(List<TrailerDto> trailers, String searchTerm);
}
