package com.example.movies_api.service;

import com.example.movies_api.dto.MovieDto;
import com.example.movies_api.strategy.sorting.MovieSortingStrategy;
import com.example.movies_api.strategy.sorting.TitleSortingStrategy;
import com.example.movies_api.strategy.sorting.YearSortingStrategy;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Serwis sortowania filmów wykorzystujący wzorzec Strategy
 */
@Service
public class MovieSortingService {
    
    private final Map<String, MovieSortingStrategy> strategies = new ConcurrentHashMap<>();

    public MovieSortingService(TitleSortingStrategy titleSortingStrategy, 
                              YearSortingStrategy yearSortingStrategy) {
        // Rejestracja dostępnych strategii
        strategies.put("title", titleSortingStrategy);
        strategies.put("year", yearSortingStrategy);
    }

    /**
     * Sortuje filmy według wybranej strategii
     * 
     * @param movies lista filmów do posortowania
     * @param sortBy nazwa strategii sortowania (title, year)
     * @return posortowana lista filmów
     */
    public List<MovieDto> sortMovies(List<MovieDto> movies, String sortBy) {
        // Wybór strategii na podstawie parametru
        MovieSortingStrategy strategy = strategies.getOrDefault(
                sortBy.toLowerCase(), 
                strategies.get("title")); // domyślnie sortowanie po tytule
        
        // Sortowanie z użyciem wybranej strategii
        return strategy.sort(new ArrayList<>(movies));
    }

    /**
     * Zwraca dostępne nazwy strategii sortowania
     */
    public List<String> getAvailableSortingStrategies() {
        return new ArrayList<>(strategies.keySet());
    }
}
