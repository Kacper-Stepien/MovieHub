package com.example.movies_api.facade.movie_facade;

import com.example.movies_api.dto.MovieDto;
import com.example.movies_api.dto.MovieSaveDto;
import com.example.movies_api.dto.RatingRequestDto;

import java.util.List;

public interface MovieFacade {
    List<MovieDto> getAllMovies();
    void createMovieWithRating(MovieSaveDto movieDto, RatingRequestDto ratingDto);
}
