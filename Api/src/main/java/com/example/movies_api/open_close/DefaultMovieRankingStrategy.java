package com.example.movies_api.open_close;

import com.example.movies_api.model.Movie;

public class DefaultMovieRankingStrategy implements MovieRankingStrategy {
    @Override
    public double calculateRanking(Movie movie) {
        if (movie.getRatings() == null || movie.getRatings().isEmpty()) {
            return 0.0;
        }
        double avgRating = movie.getRatings().stream()
                .mapToDouble(r -> r.getRating().value())
                .average()
                .orElse(0.0);
        int count = movie.getRatings().size();
        double weight = Math.min(1.0, count / 10.0);
        return avgRating * weight;
    }
}
