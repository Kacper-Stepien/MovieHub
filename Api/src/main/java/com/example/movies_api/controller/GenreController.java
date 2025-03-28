package com.example.movies_api.controller;

import com.example.movies_api.dto.GenreDto;
import com.example.movies_api.dto.GenreTreeDto;
import com.example.movies_api.dto.MovieDto;
import com.example.movies_api.model.Genre;
import com.example.movies_api.service.GenreService;
import com.example.movies_api.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/genres")
@RequiredArgsConstructor
public class GenreController {
    private final GenreService genreService;
    private final MovieService movieService;

    @GetMapping("/{name}")
    public ResponseEntity<List<MovieDto>> getGenreMovies(@PathVariable String name) {
        GenreDto genre = genreService.findGenreByName(name);
        return ResponseEntity.ok(movieService.findMoviesByGenreName(name));
    }

    @GetMapping("/details/{name}")
    public ResponseEntity<GenreDto> getGenreDetails(@PathVariable String name) {
        GenreDto genreDto = genreService.findGenreByName(name);
        return ResponseEntity.ok(genreDto);
    }

    @GetMapping()
    public ResponseEntity<List<GenreDto>> getGenreList() {
        return ResponseEntity.ok(genreService.findAllGenres());
    }

    // Kompozyt 3 //////////////////////////////////////////////////////////////////////////////////////////////////////
    @PostMapping("/create-sub")
    public ResponseEntity<Long> createSubGenre(
            @RequestParam Long parentId,
            @RequestParam String name,
            @RequestParam(required = false, defaultValue = "") String description
    ) {
        Genre child = genreService.createSubGenre(parentId, name, description);
        return ResponseEntity.ok(child.getId());
    }

    @GetMapping("/{id}/show-tree")
    public ResponseEntity<String> showGenreTree(@PathVariable Long id) {
        String tree = genreService.showGenreTree(id);
        return ResponseEntity.ok(tree);
    }

    @GetMapping("/{id}/count-subgenres")
    public ResponseEntity<Integer> countSubgenres(@PathVariable Long id) {
        int count = genreService.countSubgenres(id);
        return ResponseEntity.ok(count);
    }

    @GetMapping("/all-tree")
    public ResponseEntity<List<GenreTreeDto>> getAllGenresAsTree() {
        List<GenreTreeDto> tree = genreService.getAllGenresAsTree();
        return ResponseEntity.ok(tree);
    }
}