package com.example.movies_api.facade.movie_facade;

import com.example.movies_api.dto.MovieDto;
import com.example.movies_api.dto.MovieSaveDto;
import com.example.movies_api.dto.RatingRequestDto;
import com.example.movies_api.model.Movie;
import com.example.movies_api.movie_data_provider.ExternalMovieProvider;
import com.example.movies_api.movie_data_provider.LocalMovieProvider;
import com.example.movies_api.service.GenreService;
import com.example.movies_api.service.MovieService;
import com.example.movies_api.service.RatingService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

//@Component
@Service
public class MovieFacadeImpl implements MovieFacade {

    private final LocalMovieProvider localProvider;
    private final ExternalMovieProvider externalProvider;

    private final MovieService movieService;
    private final RatingService ratingService;

    public MovieFacadeImpl(LocalMovieProvider localProvider,
                           ExternalMovieProvider externalProvider,
                           MovieService movieService,
                           RatingService ratingService) {
        this.localProvider = localProvider;
        this.externalProvider = externalProvider;
        this.movieService = movieService;
        this.ratingService = ratingService;
    }

    @Override
    public List<MovieDto> getAllMovies() {
        List<MovieDto> localMovies = localProvider.getMovies();
        List<MovieDto> externalMovies = externalProvider.getMovies();

        // Porównujemy po tytule, case-insensitive
        Set<String> localTitles = localMovies.stream()
                .map(m -> m.getTitle().toLowerCase())
                .collect(Collectors.toSet());

        List<MovieDto> uniqueExternal = externalMovies.stream()
                .filter(movie -> !localTitles.contains(movie.getTitle().toLowerCase()))
                .toList();

        List<MovieDto> all = new ArrayList<>(localMovies);
        all.addAll(uniqueExternal);
        return all;
    }

    @Override
    public void createMovieWithRating(
            MovieSaveDto movieDto,
            RatingRequestDto ratingDto
    ) {
        // 1. Zapisujemy film w bazie
        Movie movie = movieService.addMovie(movieDto);


        // 2. Dodajemy ocenę
        String currentUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        ratingDto.setMovieId(movie.getId());
        ratingService.addOrUpdateRating(
                currentUserEmail,
                ratingDto.getMovieId(),
                ratingDto.getRating()
        );
    }

}
