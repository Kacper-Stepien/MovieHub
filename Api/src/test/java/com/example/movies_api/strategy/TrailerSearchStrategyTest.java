package com.example.movies_api.strategy;

import com.example.movies_api.dto.TrailerDto;
import com.example.movies_api.strategy.trailer_search.DescriptionSearchStrategy;
import com.example.movies_api.strategy.trailer_search.TitleSearchStrategy;
import com.example.movies_api.strategy.trailer_search.TrailerSearchStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TrailerSearchStrategyTest {

    private List<TrailerDto> trailers;
    private TrailerSearchStrategy titleStrategy;
    private TrailerSearchStrategy descriptionStrategy;

    @BeforeEach
    public void setUp() {
        // Inicjalizacja strategii
        titleStrategy = new TitleSearchStrategy();
        descriptionStrategy = new DescriptionSearchStrategy();

        // Przygotowanie testowych danych
        trailers = new ArrayList<>();
        
        TrailerDto trailer1 = new TrailerDto(1L, "Avengers Action Trailer", "yt123", 
                "Epic action movie about superheroes", "thumbnail1.jpg");
        
        TrailerDto trailer2 = new TrailerDto(2L, "Romantic Comedy", "yt456", 
                "Hilarious action comedy with romance", "thumbnail2.jpg");
        
        TrailerDto trailer3 = new TrailerDto(3L, "Documentary", "yt789", 
                "Interesting documentary about nature", "thumbnail3.jpg");
        
        trailers.add(trailer1);
        trailers.add(trailer2);
        trailers.add(trailer3);
    }

    @Test
    public void testTitleSearchStrategy() {
        // Wyszukiwanie po tytule - "Action"
        List<TrailerDto> results = titleStrategy.search(trailers, "Action");
        
        assertEquals(1, results.size());
        assertEquals("Avengers Action Trailer", results.get(0).getTitle());
    }

    @Test
    public void testDescriptionSearchStrategy() {
        // Wyszukiwanie po opisie - "action"
        List<TrailerDto> results = descriptionStrategy.search(trailers, "action");
        
        assertEquals(2, results.size());
        assertTrue(results.stream().anyMatch(t -> t.getTitle().equals("Avengers Action Trailer")));
        assertTrue(results.stream().anyMatch(t -> t.getTitle().equals("Romantic Comedy")));
    }

    @Test
    public void testEmptyResults() {
        // Wyszukiwanie bez wyników
        List<TrailerDto> results = titleStrategy.search(trailers, "nonexistent");
        assertEquals(0, results.size());
    }
}
