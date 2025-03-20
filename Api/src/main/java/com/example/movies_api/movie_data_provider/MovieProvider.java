package com.example.movies_api.movie_data_provider;

import com.example.movies_api.dto.MovieDto;
import com.example.movies_api.model.Movie;

import java.util.List;

public interface MovieProvider {
    List<MovieDto> getMovies();
}
