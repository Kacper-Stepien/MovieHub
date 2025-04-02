package com.example.movies_api.facade.movie_facade;

import com.example.movies_api.dto.MovieDto;
import com.example.movies_api.dto.MovieSaveDto;
import com.example.movies_api.dto.RatingRequestDto;

//1/3 movie facade that hides fetching the data from different sources - if the data is not found in the local database it is fetched from external source
//[previously] - that file did not exist and was implemented from scratch
import java.util.List;

public interface MovieFacade {
    List<MovieDto> getAllMovies();
    void createMovieWithRating(MovieSaveDto movieDto, RatingRequestDto ratingDto);
}
