package com.example.movies_api.service;

import com.example.movies_api.dto.MovieDto;
import com.example.movies_api.strategy.sorting.TitleSortingStrategy;
import com.example.movies_api.strategy.sorting.YearSortingStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MovieSortingServiceTest {
    
    private MovieSortingService sortingService;
    private List<MovieDto> sampleMovies;
    
    @BeforeEach
    public void setUp() {
        // Inicjalizacja serwisu i przykładowych danych
        sortingService = new MovieSortingService(
                new TitleSortingStrategy(),
                new YearSortingStrategy());
        
        sampleMovies = new ArrayList<>();
        
        MovieDto movie1 = new MovieDto();
        movie1.setId(1L);
        movie1.setTitle("Inception");
        movie1.setReleaseYear(2010);
        
        MovieDto movie2 = new MovieDto();
        movie2.setId(2L);
        movie2.setTitle("Avatar");
        movie2.setReleaseYear(2009);
        
        MovieDto movie3 = new MovieDto();
        movie3.setId(3L);
        movie3.setTitle("The Dark Knight");
        movie3.setReleaseYear(2008);
        
        MovieDto movie4 = new MovieDto();
        movie4.setId(4L);
        movie4.setTitle("Avengers: Endgame");
        movie4.setReleaseYear(2019);
        
        sampleMovies.add(movie1);
        sampleMovies.add(movie2);
        sampleMovies.add(movie3);
        sampleMovies.add(movie4);
    }
    
    @Test
    public void testSortByTitle() {
        List<MovieDto> sorted = sortingService.sortMovies(sampleMovies, "title");
        
        assertEquals(4, sorted.size());
        assertEquals("Avatar", sorted.get(0).getTitle());
        assertEquals("Avengers: Endgame", sorted.get(1).getTitle());
    }
    
    @Test
    public void testSortByYear() {
        List<MovieDto> sorted = sortingService.sortMovies(sampleMovies, "year");
        
        assertEquals(4, sorted.size());
        assertEquals(2019, sorted.get(0).getReleaseYear());
        assertEquals(2008, sorted.get(3).getReleaseYear());
    }
    
    @Test
    public void testDefaultSortingStrategy() {
        // Przy nieprawidłowej nazwie strategii powinno użyć domyślnej (title)
        List<MovieDto> sorted = sortingService.sortMovies(sampleMovies, "invalid_strategy");
        
        assertEquals("Avatar", sorted.get(0).getTitle());
    }
    
    @Test
    public void testAvailableStrategies() {
        List<String> strategies = sortingService.getAvailableSortingStrategies();
        
        assertEquals(2, strategies.size());
        assertTrue(strategies.contains("title"));
        assertTrue(strategies.contains("year"));
    }
}
