package com.example.movies_api.controller;

import com.example.movies_api.functional.StreamProcessingExamples;
import com.example.movies_api.model.Genre;
import com.example.movies_api.model.Movie;
import com.example.movies_api.repository.GenreRepository;
import com.example.movies_api.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping("/api/streams-demo")
@RequiredArgsConstructor
public class StreamDemoController {

    private final MovieRepository movieRepository;
    private final GenreRepository genreRepository;

    /**
     * Demonstration of processing a list of movies using streams
     */
    @GetMapping("/movies")
    public ResponseEntity<Map<String, Object>> demoMovieListProcessing(
            @RequestParam(defaultValue = "2020") int year) {
        
        Map<String, Object> result = new HashMap<>();
        
        // Get all movies from the database
        List<Movie> allMovies = movieRepository.findAll();
        
        // Process movies using streams
        List<String> filteredTitles = StreamProcessingExamples.processMoviesList(allMovies, year);
        
        result.put("inputSize", allMovies.size());
        result.put("year", year);
        result.put("filteredTitles", filteredTitles);
        result.put("outputSize", filteredTitles.size());
        
        return ResponseEntity.ok(result);
    }

    /**
     * Demonstration of processing a map of ratings using streams
     */
    @GetMapping("/ratings")
    public ResponseEntity<Map<String, Object>> demoRatingsMapProcessing() {
        // Create a sample map of movie ratings by genre
        Map<String, List<Double>> genreRatings = generateSampleRatings();
        
        // Process the map using streams
        Map<String, Double> averageRatings = StreamProcessingExamples.processRatingsMap(genreRatings);
        
        Map<String, Object> result = new HashMap<>();
        result.put("originalRatings", genreRatings);
        result.put("averageRatings", averageRatings);
        
        return ResponseEntity.ok(result);
    }

    /**
     * Demonstration of processing a set of genres using streams
     */
    @GetMapping("/genres")
    public ResponseEntity<Map<String, Object>> demoGenresSetProcessing() {
        // Get all genres from the database
        Set<Genre> allGenres = new HashSet<>(genreRepository.findAll());
        
        // Process the set of genres using streams
        Set<String> processedGenres = StreamProcessingExamples.processGenresSet(allGenres);
        
        Map<String, Object> result = new HashMap<>();
        result.put("inputSize", allGenres.size());
        result.put("processedGenres", processedGenres);
        result.put("outputSize", processedGenres.size());
        
        return ResponseEntity.ok(result);
    }

    /**
     * Generates a sample map of ratings for different genres
     */
    private Map<String, List<Double>> generateSampleRatings() {
        Map<String, List<Double>> ratings = new HashMap<>();
        
        ratings.put("Action", Arrays.asList(4.5, 3.2, 4.8, 5.0, 3.9));
        ratings.put("Comedy", Arrays.asList(4.2, 3.9, 3.5, 4.3));
        ratings.put("Drama", Arrays.asList(4.7, 4.9, 5.0, 4.8, 4.6));
        ratings.put("Horror", Arrays.asList(3.5, 3.2, 2.8, 4.0));
        ratings.put("Sci-Fi", Arrays.asList(4.1, 4.4, 3.9, 4.7));
        ratings.put("Documentary", Arrays.asList(4.8, 4.6, 4.7));
        ratings.put("Animation", Arrays.asList(4.3, 4.5, 3.8, 4.0, 4.2));
        
        return ratings;
    }

    /**
     * Endpoint showing various stream operations
     */
    @GetMapping("/operations")
    public ResponseEntity<String> demoStreamOperations() {
        StreamProcessingExamples.demonstrateStreamOperations();
        return ResponseEntity.ok("Check the console for results of stream operations demonstration");
    }
}
