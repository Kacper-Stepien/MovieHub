package com.example.movies_api.controller;

import com.example.movies_api.dto.MovieDto;
import com.example.movies_api.movie_data_provider.ExternalMovieProvider;
import com.example.movies_api.movie_data_provider.LocalMovieProvider;
import com.example.movies_api.service.MovieService;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movies")
@RequiredArgsConstructor
public class MovieController {
    private final MovieService movieService;
    private final LocalMovieProvider localMovieProvider;
    private final ExternalMovieProvider externalMovieProvider;

    //@Cacheable("movies")
    @GetMapping("/all")
    public ResponseEntity<List<MovieDto>> getAllMovies(@RequestParam(required = false, defaultValue = "local")String source) {
        return ResponseEntity.ok(movieService.getMovies(source));
    }

    @GetMapping("/promoted")
    public ResponseEntity<List<MovieDto>> getPromotedMovies() {
        return ResponseEntity.ok(movieService.findAllPromotedMovies());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovieDto> getMovie(@PathVariable long id) {
        return ResponseEntity.ok(movieService.findMovieById(id));
    }

    @GetMapping("/top10")
    public ResponseEntity<List<MovieDto>> findTop10() {
        return ResponseEntity.ok(movieService.findTopMovies(10));
    }

    @GetMapping("/filters")
    public ResponseEntity<List<MovieDto>> findMovies(@RequestParam(required = false) String genre,
                                                     @RequestParam(required = false) Integer releaseYear,
                                                     @RequestParam(required = false, defaultValue = "1") int page) {

        return ResponseEntity.ok(movieService.findAllWithFilters(genre, releaseYear, page - 1));
    }

    @GetMapping("/error-test")
    public void throwError() {
        throw new EntityNotFoundException("Film nie zostal znaleziony");
    }

}
