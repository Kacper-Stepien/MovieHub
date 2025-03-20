package com.example.movies_api.controller.movie_management_adapter;

import com.example.movies_api.dto.MovieDto;
import com.example.movies_api.dto.MovieSaveDto;
import com.example.movies_api.dto.UpdateMovieDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface MovieManagementAdapter {
    String addMovie(MovieSaveDto movieDto, MultipartFile poster); // Returns response in either XML or JSON
    String deleteMovie(Long movieId); // Returns response in either XML or JSON
    String updateMovie(Long movieId, UpdateMovieDto updateMovieDto); // Returns response in either XML or JSON
}