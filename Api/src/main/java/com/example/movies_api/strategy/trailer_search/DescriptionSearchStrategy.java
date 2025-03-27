package com.example.movies_api.strategy.trailer_search;

import com.example.movies_api.dto.TrailerDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class DescriptionSearchStrategy implements TrailerSearchStrategy {
    @Override
    public List<TrailerDto> search(List<TrailerDto> trailers, String searchTerm) {
        String term = searchTerm.toLowerCase();
        return trailers.stream()
            .filter(trailer -> trailer.getDescription() != null && 
                    trailer.getDescription().toLowerCase().contains(term))
            .collect(Collectors.toList());
    }
}
