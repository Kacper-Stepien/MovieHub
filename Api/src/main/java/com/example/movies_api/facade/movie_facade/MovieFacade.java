package com.example.movies_api.facade.movie_facade;

import com.example.movies_api.dto.MovieDto;
import java.util.List;

public interface MovieFacade {
    List<MovieDto> getAllMovies();
}
