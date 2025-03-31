package com.example.movies_api.controller;

import com.example.movies_api.dto.MovieDto;
import com.example.movies_api.functional.MovieFilter;
import com.example.movies_api.functional.MovieTransformer;
import com.example.movies_api.service.MovieFunctionalService;
import com.example.movies_api.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Demo controller for functional interfaces
 */
@RestController
@RequestMapping("/api/functional-demo")
@RequiredArgsConstructor
public class FunctionalDemoController {

    private final MovieService movieService;
    private final MovieFunctionalService functionalService;

    /**
     * Endpoint demonstrating movie filtering using functional interface
     */
    @GetMapping("/filter")
    public ResponseEntity<Map<String, Object>> filterMovies(
            @RequestParam(defaultValue = "0") double minRating,
            @RequestParam(defaultValue = "0") int fromYear) {
        
        List<MovieDto> allMovies = movieService.findAllMovies();
        Map<String, Object> result = new HashMap<>();
        result.put("totalMovies", allMovies.size());
        
        // Using MovieFilter functional interface with lambda expression for rating
        MovieFilter ratingFilter = movie -> movie.getAvgRating() >= minRating;
        List<MovieDto> filteredByRating = functionalService.filterMovies(allMovies, ratingFilter);
        result.put("moviesWithRatingAbove" + minRating, filteredByRating.size());
        
        // Using MovieFilter for year filtering
        MovieFilter yearFilter = movie -> movie.getReleaseYear() >= fromYear;
        List<MovieDto> filteredByYear = functionalService.filterMovies(allMovies, yearFilter);
        result.put("moviesFromYear" + fromYear, filteredByYear.size());
        
        // Combined filter - both rating AND year
        MovieFilter combinedFilter = movie -> movie.getAvgRating() >= minRating && movie.getReleaseYear() >= fromYear;
        List<MovieDto> combinedFilteredMovies = functionalService.filterMovies(allMovies, combinedFilter);
        
        // Store the actual movie list in the result
        result.put("filteredMovies", combinedFilteredMovies);
        result.put("filteredCount", combinedFilteredMovies.size());
        
        return ResponseEntity.ok(result);
    }

    /**
     * Endpoint demonstrating movie transformation using functional interface
     */
    @GetMapping("/transform")
    public ResponseEntity<Map<String, Object>> transformMovies() {
        List<MovieDto> allMovies = movieService.findAllMovies();
        Map<String, Object> result = new HashMap<>();
        
        // Using MovieTransformer functional interface with lambda expression
        MovieTransformer<String> titleExtractor = movie -> movie.getTitle();
        List<String> titles = functionalService.transformMovies(allMovies, titleExtractor);
        result.put("titles", titles);
        
        // Another transformation - movie summary
        MovieTransformer<Map<String, Object>> summaryExtractor = movie -> {
            Map<String, Object> summary = new HashMap<>();
            summary.put("title", movie.getTitle());
            summary.put("year", movie.getReleaseYear());
            summary.put("rating", movie.getAvgRating());
            return summary;
        };
        
        List<Map<String, Object>> summaries = functionalService.transformMovies(allMovies, summaryExtractor);
        result.put("summaries", summaries);
        
        return ResponseEntity.ok(result);
    }

    /**
     * Demo endpoint showcasing lambda expressions for movies
     */
    @GetMapping("/demo")
    public ResponseEntity<String> runFunctionalDemo() {
        List<MovieDto> movies = movieService.findAllMovies();
        functionalService.demonstrateFunctionalInterfaces(movies);
        return ResponseEntity.ok("Functional interfaces demonstration completed successfully. Check server logs.");
    }
}
