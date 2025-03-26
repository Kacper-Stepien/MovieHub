package com.example.movies_api.controller;


import com.example.movies_api.controller.movie_management_adapter.JsonMovieManagementAdapter;
import com.example.movies_api.controller.movie_management_adapter.XmlMovieManagementAdapter;
import com.example.movies_api.dto.MovieCreationRequest;
import com.example.movies_api.dto.MovieGenresDto;
import com.example.movies_api.dto.MovieSaveDto;
import com.example.movies_api.dto.UpdateMovieDto;
import com.example.movies_api.facade.movie_facade.MovieFacade;
import com.example.movies_api.model.Movie;
import com.example.movies_api.service.MovieService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatchException;
import com.github.fge.jsonpatch.mergepatch.JsonMergePatch;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
@RestController
@RequestMapping("/admin")
public class MovieManagementController {


    private final JsonMovieManagementAdapter jsonAdapter;
    private final XmlMovieManagementAdapter xmlAdapter;

    private final MovieFacade movieFacade;
    private final MovieService movieService;

    public MovieManagementController(JsonMovieManagementAdapter jsonAdapter,
                                     XmlMovieManagementAdapter xmlAdapter,
                                     MovieFacade movieFacade,
                                     MovieService movieService) {
        this.jsonAdapter = jsonAdapter;
        this.xmlAdapter = xmlAdapter;
        this.movieFacade=movieFacade;
        this.movieService=movieService;
    }

    @PostMapping(value = "/add-movie", consumes = {"multipart/form-data"}, produces = "application/json")
    public ResponseEntity<String> addMovie(
            @ModelAttribute MovieSaveDto movieDto,
            @RequestPart(value = "poster", required = false) MultipartFile poster) {

        List<String> allowedTypes = Arrays.asList("FULL_LENGTH", "TRAILER", "SERIES");
        if (!allowedTypes.contains(movieDto.getMovieType())) {
            String errMsg = "Nieprawidłowy typ filmu: " + movieDto.getMovieType()
                    + ". Dozwolone wartości: " + allowedTypes;
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errMsg);
        }

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


    //facade for creating movie with genres and rating
    @PostMapping("/create-full")
    public ResponseEntity<String> createMovieWithRating(
            @RequestBody MovieCreationRequest request) {
        movieFacade.createMovieWithRating(request.getMovieDto(), request.getRatingDto());
        return ResponseEntity.ok("Movie created with rating.");
    }

    //memento pattern for restoring movie list
    @GetMapping("/undo-last-add")
    public ResponseEntity<String> undoLastMovieAdd() {
        try {
            movieService.undoLastMovieAdd();
            return ResponseEntity.ok("Operacja cofnięta – ostatnio dodany film został usunięty.");
        } catch (IllegalStateException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Brak operacji do cofnięcia.");
        }
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