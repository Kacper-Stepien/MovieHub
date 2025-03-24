package com.example.movies_api.movie_data_provider;

import com.example.movies_api.dto.MovieDto;
import com.example.movies_api.mapper.MovieDtoMapper;
import com.example.movies_api.model.Movie;
import com.example.movies_api.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Qualifier("localMovieProvider")
public class LocalMovieProvider implements MovieProvider {
    private final MovieRepository movieRepository;

    public LocalMovieProvider(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @Override
    public List<MovieDto> getMovies() {
        return movieRepository.findAll().stream()
                .map(MovieDtoMapper::map)
                .toList();
    }
}