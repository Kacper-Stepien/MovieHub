package com.example.movies_api.controller.movie_management_adapter;

import com.example.movies_api.dto.MovieDto;
import com.example.movies_api.dto.MovieSaveDto;
import com.example.movies_api.dto.UpdateMovieDto;
import com.example.movies_api.service.MovieService;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Component
public class XmlMovieManagementAdapter implements MovieManagementAdapter {

    private final MovieService movieService;
    private final XmlMapper xmlMapper = new XmlMapper();

    public XmlMovieManagementAdapter(MovieService movieService) {
        this.movieService = movieService;
    }

    @Override
    public String addMovie(MovieSaveDto movieDto, MultipartFile poster) {
        movieDto.setPoster(poster);
        movieService.addMovie(movieDto);
        return "<response><message>Movie added successfully</message></response>";
    }


    @Override
    public String deleteMovie(Long movieId) {
        movieService.deleteMovie(movieId);
        return "<response><message>Movie deleted successfully</message></response>";
    }

    @Override
    public String updateMovie(Long movieId, UpdateMovieDto updateMovieDto) {
        movieService.updateMovie(movieId, updateMovieDto);
        return "<response><message>Movie updated successfully</message></response>";
    }

    //Open-Close Principle 1/3 (data steering) [added lines]
    public String getSupportedContentType() {
        return "application/xml";
    }
}