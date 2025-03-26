package com.example.movies_api.facade.movie_facade;

import com.example.movies_api.dto.MovieDto;
import com.example.movies_api.movie_data_provider.ExternalMovieProvider;
import com.example.movies_api.movie_data_provider.LocalMovieProvider;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class MovieFacadeImpl implements MovieFacade {

    private final LocalMovieProvider localProvider;
    private final ExternalMovieProvider externalProvider;

    public MovieFacadeImpl(LocalMovieProvider localProvider, ExternalMovieProvider externalProvider) {
        this.localProvider = localProvider;
        this.externalProvider = externalProvider;
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
}
