package com.example.movies_api.controller.movie_management_adapter;

import com.example.movies_api.dto.MovieDto;
import com.example.movies_api.dto.MovieSaveDto;
import com.example.movies_api.dto.UpdateMovieDto;
import com.example.movies_api.service.MovieService;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Component
public class JsonMovieManagementAdapter implements MovieManagementAdapter {

    private final MovieService movieService;

    public JsonMovieManagementAdapter(MovieService movieService) {
        this.movieService = movieService;
    }

    @Override
    public String addMovie(MovieSaveDto movieDto, MultipartFile poster) {
        movieDto.setPoster(poster);
        movieService.addMovie(movieDto);
        return "{\"message\": \"Movie added successfully\"}";
    }

    @Override
    public String deleteMovie(Long movieId) {
        movieService.deleteMovie(movieId);
        return "{\"message\": \"Movie deleted successfully\"}";
    }

    @Override
    public String updateMovie(Long movieId, UpdateMovieDto updateMovieDto) {
        movieService.updateMovie(movieId, updateMovieDto);
        return "{\"message\": \"Movie updated successfully\"}";
    }


    //Open-Close Principle 1/3 (data steering) [added lines]
    public String getSupportedContentType() {
        return "application/json";
    }
}