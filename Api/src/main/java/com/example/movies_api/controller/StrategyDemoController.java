package com.example.movies_api.controller;

import com.example.movies_api.dto.MovieDto;
import com.example.movies_api.dto.TrailerDto;
import com.example.movies_api.service.NotificationService;
import com.example.movies_api.service.TrailerService;
import com.example.movies_api.strategy.sorting.MovieSortingStrategy;
import com.example.movies_api.strategy.sorting.TitleSortingStrategy;
import com.example.movies_api.strategy.sorting.YearSortingStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Kontroler demonstracyjny dla wzorca Strategy
 * Pozwala na testowanie różnych strategii i obserwowanie ich działania
 */
@RestController
@RequestMapping("/api/strategy-demo")
@RequiredArgsConstructor
public class StrategyDemoController {

    private final TrailerService trailerService;
    private final TitleSortingStrategy titleSortingStrategy;
    private final YearSortingStrategy yearSortingStrategy;
    private final NotificationService notificationService;

    /**
     * Endpoint do testowania strategii wyszukiwania trailerów
     * Umożliwia wybór strategii wyszukiwania za pomocą parametru URL
     * 
     * @param searchBy parametr określający strategię wyszukiwania: "title" lub "description"
     * @param searchTerm fraza do wyszukania
     * @param source źródło danych (domyślnie "local")
     * @return wyniki wyszukiwania zgodnie z wybraną strategią
     */
    @GetMapping("/search-trailers")
    public ResponseEntity<Map<String, Object>> testTrailerSearchStrategies(
            @RequestParam(defaultValue = "title") String searchBy,
            @RequestParam String searchTerm,
            @RequestParam(defaultValue = "local") String source) {
        
        Map<String, Object> result = new HashMap<>();
        
        // Wybór strategii wyszukiwania na podstawie parametru
        List<TrailerDto> searchResults = trailerService.searchTrailersWithStrategy(searchBy, searchTerm, source);
        
        result.put("searchBy", searchBy);
        result.put("searchResults", searchResults);
        result.put("resultsCount", searchResults.size());
        
        return ResponseEntity.ok(result);
    }

    /**
     * Endpoint do testowania strategii sortowania filmów z parametrem
     * Umożliwia wybór strategii sortowania za pomocą parametru URL
     * 
     * @param sortBy parametr określający strategię sortowania: "year" lub "title"
     * @return posortowane filmy zgodnie z wybraną strategią
     */
    @GetMapping("/sort-movies-by")
    public ResponseEntity<List<MovieDto>> sortMovies(
            @RequestParam(defaultValue = "title") String sortBy) {
        
        List<MovieDto> sampleMovies = createSampleMovies();
        MovieSortingStrategy strategy;
        
        // Wybór strategii sortowania na podstawie parametru
        if ("year".equalsIgnoreCase(sortBy)) {
            strategy = yearSortingStrategy;
        } else {
            strategy = titleSortingStrategy;
        }
        
        // Sortowanie filmów wybraną strategią
        List<MovieDto> sortedMovies = strategy.sort(new ArrayList<>(sampleMovies));
        
        return ResponseEntity.ok(sortedMovies);
    }

    /**
     * Endpoint do testowania strategii sortowania filmów
     */
    @GetMapping("/sort-movies")
    public ResponseEntity<Map<String, Object>> testMovieSortingStrategies() {
        // Tworzymy przykładowe filmy
        List<MovieDto> sampleMovies = createSampleMovies();
        Map<String, Object> result = new HashMap<>();
        
        // Sortowanie po tytule
        List<MovieDto> titleSorted = titleSortingStrategy.sort(new ArrayList<>(sampleMovies));
        result.put("originalMovies", sampleMovies);
        result.put("sortedByTitle", titleSorted);
        
        // Sortowanie po roku
        List<MovieDto> yearSorted = yearSortingStrategy.sort(new ArrayList<>(sampleMovies));
        result.put("sortedByYear", yearSorted);
        
        return ResponseEntity.ok(result);
    }

    /**
     * Endpoint do testowania strategii powiadomień
     */
    @GetMapping("/test-notifications")
    public ResponseEntity<String> testNotificationStrategies(
            @RequestParam(defaultValue = "email") String notificationType,
            @RequestParam(defaultValue = "user@example.com") String recipient) {
        
        notificationService.setNotificationStrategy(notificationType);
        notificationService.sendNotification(
                recipient, 
                "Test powiadomienia", 
                "To jest testowa wiadomość wysłana za pomocą strategii " + notificationType);
        
        return ResponseEntity.ok("Wysłano powiadomienie typu: " + notificationType + " do: " + recipient);
    }
    
    /**
     * Metoda pomocnicza do tworzenia przykładowych filmów
     */
    private List<MovieDto> createSampleMovies() {
        List<MovieDto> movies = new ArrayList<>();
        
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
        
        return movies;
    }
}
