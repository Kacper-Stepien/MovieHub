package com.example.movies_api.strategy;

import com.example.movies_api.dto.MovieDto;
import com.example.movies_api.strategy.sorting.TitleSortingStrategy;
import com.example.movies_api.strategy.sorting.YearSortingStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MovieSortingStrategyTest {
    
    private List<MovieDto> movies;
    private TitleSortingStrategy titleStrategy;
    private YearSortingStrategy yearStrategy;
    
    @BeforeEach
    public void setUp() {
        titleStrategy = new TitleSortingStrategy();
        yearStrategy = new YearSortingStrategy();
        
        movies = new ArrayList<>();
        
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
        
        movies.add(movie1);
        movies.add(movie2);
        movies.add(movie3);
        movies.add(movie4);
    }
    
    @Test
    public void testTitleSortingStrategy() {
        List<MovieDto> sorted = titleStrategy.sort(movies);
        
        // Sprawdzenie, czy filmy są posortowane alfabetycznie według tytułu
        assertEquals("Avatar", sorted.get(0).getTitle());
        assertEquals("Avengers: Endgame", sorted.get(1).getTitle());
        assertEquals("Inception", sorted.get(2).getTitle());
        assertEquals("The Dark Knight", sorted.get(3).getTitle());
    }
    
    @Test
    public void testYearSortingStrategy() {
        List<MovieDto> sorted = yearStrategy.sort(movies);
        
        // Sprawdzenie, czy filmy są posortowane według roku w porządku malejącym
        assertEquals(2019, sorted.get(0).getReleaseYear());
        assertEquals(2010, sorted.get(1).getReleaseYear());
        assertEquals(2009, sorted.get(2).getReleaseYear());
        assertEquals(2008, sorted.get(3).getReleaseYear());
    }
}
