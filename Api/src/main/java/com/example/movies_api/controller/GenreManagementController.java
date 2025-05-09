package com.example.movies_api.controller;

import com.example.movies_api.dto.GenreDto;
import com.example.movies_api.model.Genre;
import com.example.movies_api.service.GenreService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatchException;
import com.github.fge.jsonpatch.mergepatch.JsonMergePatch;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class GenreManagementController {
    private final GenreService genreService;
    private final ObjectMapper objectMapper;

    @PostMapping("/add-genre")
    public ResponseEntity<Genre> addGenre(@Valid @RequestBody GenreDto genreDto) {
        Genre savedGenre = genreService.addGenre(genreDto);
        URI savedGenreUri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedGenre.getId())
                .toUri();
        return ResponseEntity.created(savedGenreUri).body(savedGenre);
    }

    // Kompozyt 3 //////////////////////////////////////////////////////////////////////////////////////////////////////
    @PostMapping("/create-subgenre")
    public ResponseEntity<Long> createSubGenre(
            @RequestParam Long parentId,
            @RequestParam String name,
            @RequestParam(required = false, defaultValue = "") String description
    ) {
        Genre child = genreService.createSubGenre(parentId, name, description);
        return ResponseEntity.ok(child.getId());
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    @PatchMapping("/update-genre/{id}")
    ResponseEntity<?> updateGenre(@PathVariable Long id,@Valid @RequestBody JsonMergePatch patch) { // @RequestBody JsonMergePatch patch: JSON zawierający tylko dane do zmiany.
        try {
            GenreDto genreDto = genreService.findGenreById(id);
            GenreDto genrePatched = applyPatch(genreDto, patch);
            genreService.updateGenre(genrePatched);

        } catch (JsonPatchException | JsonProcessingException e) {
            return ResponseEntity.internalServerError().build();
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }

    private GenreDto applyPatch(GenreDto genreDto, JsonMergePatch patch) throws JsonPatchException, JsonProcessingException {
        JsonNode genreNode = objectMapper.valueToTree(genreDto);
        JsonNode genrePatchedNode = patch.apply(genreNode);
        return objectMapper.treeToValue(genrePatchedNode, GenreDto.class);
    }

    @DeleteMapping("/delete-genre/{id}")
    public ResponseEntity<?> deleteGenre(@PathVariable long id) {
        genreService.deleteGenre(id);
        return ResponseEntity.noContent().build();
    }
}

