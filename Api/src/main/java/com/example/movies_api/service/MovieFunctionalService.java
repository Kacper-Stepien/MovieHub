package com.example.movies_api.service;

import com.example.movies_api.dto.MovieDto;
import com.example.movies_api.functional.MovieConsumer;
import com.example.movies_api.functional.MovieFilter;
import com.example.movies_api.functional.MovieTransformer;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service demonstrating the use of functional interfaces and lambda expressions
 */
@Service
public class MovieFunctionalService {

    /**
     * Filters a list of movies based on the provided predicate
     */
    public List<MovieDto> filterMovies(List<MovieDto> movies, MovieFilter filter) {
        List<MovieDto> result = new ArrayList<>();
        for (MovieDto movie : movies) {
            if (filter.test(movie)) {
                result.add(movie);
            }
        }
        return result;
    }

    /**
     * Transforms a list of movies into a list of objects of another type
     */
    public <R> List<R> transformMovies(List<MovieDto> movies, MovieTransformer<R> transformer) {
        List<R> result = new ArrayList<>();
        for (MovieDto movie : movies) {
            result.add(transformer.apply(movie));
        }
        return result;
    }

    /**
     * Performs an operation on each movie in the list
     */
    public void processMovies(List<MovieDto> movies, MovieConsumer consumer) {
        for (MovieDto movie : movies) {
            consumer.accept(movie);
        }
    }

    /**
     * Example use of functional interfaces with lambda expressions
     */
    public void demonstrateFunctionalInterfaces(List<MovieDto> movies) {
        System.out.println("--- Functional Interfaces Demonstration ---");
        
        // Example of using MovieFilter - filtering movies with rating above 4.0
        MovieFilter highRatingFilter = movie -> movie.getAvgRating() > 4.0;
        List<MovieDto> highRatedMovies = filterMovies(movies, highRatingFilter);
        System.out.println("Movies with high rating: " + highRatedMovies.size());

        // Alternatively, inline lambda
        List<MovieDto> recentMovies = filterMovies(movies, 
                movie -> movie.getReleaseYear() >= 2020);
        System.out.println("Movies from recent years: " + recentMovies.size());
        
        // Example of using MovieTransformer - extracting movie titles
        MovieTransformer<String> titleExtractor = movie -> movie.getTitle();
        List<String> movieTitles = transformMovies(movies, titleExtractor);
        System.out.println("Movie titles: " + String.join(", ", movieTitles));
        
        // Example of using MovieConsumer - logging movie information
        MovieConsumer logger = movie -> System.out.println(
                "Movie: " + movie.getTitle() + " (" + movie.getReleaseYear() + "), " +
                "Rating: " + movie.getAvgRating()
        );
        
        // Processing only highly rated movies from recent years
        List<MovieDto> trendingMovies = movies.stream()
                .filter(m -> m.getAvgRating() > 4.0)
                .filter(m -> m.getReleaseYear() >= 2020)
                .collect(Collectors.toList());
        
        processMovies(trendingMovies, logger);
    }
}
