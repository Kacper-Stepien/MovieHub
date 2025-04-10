package com.example.movies_api.open_close;

import com.example.movies_api.model.Movie;

public interface MovieRankingStrategy {
    double calculateRanking(Movie movie);
}