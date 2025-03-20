package com.example.movies_api.controller;


import com.example.movies_api.controller.movie_management_adapter.JsonMovieManagementAdapter;
import com.example.movies_api.controller.movie_management_adapter.XmlMovieManagementAdapter;
import com.example.movies_api.dto.MovieGenresDto;
import com.example.movies_api.dto.MovieSaveDto;
import com.example.movies_api.dto.UpdateMovieDto;
import com.example.movies_api.model.Movie;
import com.example.movies_api.service.MovieService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatchException;
import com.github.fge.jsonpatch.mergepatch.JsonMergePatch;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.NoSuchElementException;
@RestController
@RequestMapping("/admin")
public class MovieManagementController {


    private final JsonMovieManagementAdapter jsonAdapter;
    private final XmlMovieManagementAdapter xmlAdapter;


    public MovieManagementController(JsonMovieManagementAdapter jsonAdapter, XmlMovieManagementAdapter xmlAdapter) {
        this.jsonAdapter = jsonAdapter;
        this.xmlAdapter = xmlAdapter;
    }

    @PostMapping(value = "/add-movie", consumes = {"multipart/form-data"}, produces = "application/json")
    public ResponseEntity<String> addMovie(
            @ModelAttribute MovieSaveDto movieDto,
            @RequestPart(value = "poster", required = false) MultipartFile poster) {

        String response = jsonAdapter.addMovie(movieDto, poster);

        URI savedMovieUri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(movieDto.getTitle()) // Assuming title is unique
                .toUri();

        return ResponseEntity.created(savedMovieUri).body(response);
    }

    @PatchMapping(value = "/update-movie/{id}", produces = {"application/json", "application/xml"})
    public ResponseEntity<String> updateMovie(@PathVariable Long id, @RequestBody UpdateMovieDto updateMovieDto, @RequestHeader("Accept") String acceptHeader) {
        String response = "application/xml".equalsIgnoreCase(acceptHeader) ? xmlAdapter.updateMovie(id, updateMovieDto) : jsonAdapter.updateMovie(id, updateMovieDto);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping(value = "/delete-movie/{id}", produces = {"application/json", "application/xml"})
    public ResponseEntity<String> deleteMovie(@PathVariable long id, @RequestHeader("Accept") String acceptHeader) {
        String response = "application/xml".equalsIgnoreCase(acceptHeader) ? xmlAdapter.deleteMovie(id) : jsonAdapter.deleteMovie(id);
        return ResponseEntity.ok(response);
    }
}

/*
    //before movie management adapter
@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class MovieManagementController {
    private final MovieService movieService;
    private final ObjectMapper objectMapper;

    @PostMapping("/add-movie")
    public ResponseEntity<Movie> addMovie(@Valid @ModelAttribute MovieSaveDto movie) {
        Movie savedMovie = movieService.addMovie(movie);
        URI savedMovieUri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedMovie.getId())
                .toUri();
        return ResponseEntity.created(savedMovieUri).body(savedMovie);
    }


    @PatchMapping("/update-movie/{id}")
    public ResponseEntity<?> updateMovie(@PathVariable Long id, @RequestBody UpdateMovieDto updateMovieDto) {
        movieService.updateMovie(id, updateMovieDto);
        return ResponseEntity.noContent().build();
    }


    private MovieGenresDto applyPatch(MovieGenresDto movieDto, JsonMergePatch patch) throws JsonPatchException, JsonProcessingException {
        JsonNode movieNode = objectMapper.valueToTree(movieDto);
        JsonNode moviePatchedNode = patch.apply(movieNode);
        return objectMapper.treeToValue(moviePatchedNode, MovieGenresDto.class);
    }

    @DeleteMapping("/delete-movie/{id}")
    public ResponseEntity<?> deleteMovie(@PathVariable long id) {
        movieService.deleteMovie(id);
        return ResponseEntity.noContent().build();
    }

}
*/