package com.example.movies_api.controller;

import com.example.movies_api.dto.MovieDto;
import com.example.movies_api.exception.BadRequestException;
import com.example.movies_api.facade.movie_facade.MovieFacade;
import com.example.movies_api.movie_data_provider.ExternalMovieProvider;
import com.example.movies_api.movie_data_provider.LocalMovieProvider;
import com.example.movies_api.service.MovieService;
import com.example.movies_api.service.MovieSortingService;

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
    private final MovieFacade movieFacade;
    private final MovieSortingService sortingService;

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

    @GetMapping("/query")
    public ResponseEntity<List<MovieDto>> queryMovies(@RequestParam String expression) {
        try {
            return ResponseEntity.ok(movieService.findMoviesByQueryExpression(expression));
        } catch (IllegalArgumentException e) {
            throw new BadRequestException("Invalid query expression: " + e.getMessage());
        }
    }

    @GetMapping("/error-test")
    public void throwError() {
        throw new EntityNotFoundException("Film nie zostal znaleziony");
    }

    // Fasada – pobierz wszystkie filmy z logiką fallbacku
    @GetMapping("/facade/all-movie")
    public ResponseEntity<List<MovieDto>> getAllMoviesViaFacade() {
        return ResponseEntity.ok(movieFacade.getAllMovies());
    }

    /**
     * Pobiera listę filmów z opcją sortowania
     * 
     * @param sortBy strategia sortowania (np. "title", "year")
     * @return posortowana lista filmów
     */
    @GetMapping("/sorted")
    public ResponseEntity<List<MovieDto>> getMoviesSorted(
            @RequestParam(defaultValue = "title") String sortBy) {
        
        // Pobieramy wszystkie filmy
        List<MovieDto> allMovies = movieService.findAllMovies();
        
        // Sortujemy filmy używając wybranej strategii
        List<MovieDto> sortedMovies = sortingService.sortMovies(allMovies, sortBy);
        
        return ResponseEntity.ok(sortedMovies);
    }
    
    /**
     * Zwraca dostępne opcje sortowania
     */
    @GetMapping("/sorting-options")
    public ResponseEntity<List<String>> getSortingOptions() {
        return ResponseEntity.ok(sortingService.getAvailableSortingStrategies());
    }

}
