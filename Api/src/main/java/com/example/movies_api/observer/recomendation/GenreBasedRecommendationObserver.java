package com.example.movies_api.observer.recomendation;
import com.example.movies_api.model.Movie;
import com.example.movies_api.repository.MovieRepository;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

//observer 3/3 added file
public class GenreBasedRecommendationObserver implements MovieRecommendationObserver {

    private final MovieRepository movieRepository;

    public GenreBasedRecommendationObserver(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @Override
    public void update(Movie newMovie) {
        System.out.println("\n[Observer] New movie added: " + newMovie.getTitle());
        System.out.println("[Observer] Fetching random recommendations from the same genre...");

        List<Movie> recommendations = movieRepository.findAll().stream()
                .filter(m -> m.getGenre().equals(newMovie.getGenre()) && !m.getTitle().equals(newMovie.getTitle()))
                .limit(3)
                .collect(Collectors.toList());

        if (recommendations.isEmpty()) {
            System.out.println("No recommendations available.");
        } else {
            recommendations.forEach(movie ->
                    System.out.println("Recommended: " + movie.getTitle() + " [" + movie.getGenre() + "]"));
        }
    }
}
