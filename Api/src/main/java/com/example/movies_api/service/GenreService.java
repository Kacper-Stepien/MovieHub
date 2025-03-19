package com.example.movies_api.service;


import com.example.movies_api.dto.GenreDto;
import com.example.movies_api.dto.GenreTreeDto;
import com.example.movies_api.exception.BadRequestException;
import com.example.movies_api.exception.ResourceNotFoundException;
import com.example.movies_api.mapper.GenreDtoMapper;
import com.example.movies_api.model.Genre;
import com.example.movies_api.repository.GenreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.StreamSupport;

import static com.example.movies_api.constants.Messages.GENRE_EXISTS;
import static com.example.movies_api.constants.Messages.GENRE_NOT_FOUND;

// Kompozyt 3
@Service
@RequiredArgsConstructor
public class GenreService {
    private final GenreRepository genreRepository;
    private final GenreDtoMapper genreDtoMapper;

    public GenreDto findGenreByName(String name) {
        return genreRepository.findByNameIgnoreCase(name)
                .map(GenreDtoMapper::map)
                .orElseThrow(() -> new ResourceNotFoundException(GENRE_NOT_FOUND));
    }

    public List<GenreDto> findAllGenres() {
        return StreamSupport.stream(genreRepository.findAll().spliterator(), false)
                .map(GenreDtoMapper::map)
                .toList();
    }

    public Genre addGenre(GenreDto genreDto) {
        if (genreRepository.findByNameIgnoreCase(genreDto.getName()).isPresent()) {
            throw new BadRequestException(GENRE_EXISTS);
        }
        Genre genreToSave = new Genre();
        genreToSave.setName(genreDto.getName());
        genreToSave.setDescription(genreDto.getDescription());
//         parent = null domyślnie
        genreRepository.save(genreToSave);
        return genreToSave;
    }

    public GenreDto findGenreById(long id) {
        return genreRepository.findById(id)
                .map(GenreDtoMapper::map)
                .orElseThrow(() -> new ResourceNotFoundException(GENRE_NOT_FOUND));
    }

    public void updateGenre(GenreDto genreDto) {
        if (!genreRepository.existsById(genreDto.getId())) {
            throw new ResourceNotFoundException(GENRE_NOT_FOUND);
        }

        genreRepository.findByNameIgnoreCase(genreDto.getName())
                .filter(existingGenre -> !existingGenre.getId().equals(genreDto.getId()))
                .ifPresent(existingGenre -> {
                    throw new BadRequestException(GENRE_EXISTS);
                });

        Genre genre = genreDtoMapper.mapToGenre(genreDto);
        genreRepository.save(genre);
    }

    public void deleteGenre(long id) {
        if (!genreRepository.existsById(id)) {
            throw new ResourceNotFoundException(GENRE_NOT_FOUND);
        }
        genreRepository.deleteById(id);
    }

    // Kompozyt 3 //////////////////////////////////////////////////////////////////////////////////////////////////////
    public Genre createSubGenre(Long parentId, String name, String description) {
        Genre parent = genreRepository.findById(parentId)
                .orElseThrow(() -> new ResourceNotFoundException(GENRE_NOT_FOUND));

        if (genreRepository.findByNameIgnoreCase(name).isPresent()) {
            throw new BadRequestException(GENRE_EXISTS);
        }

        Genre child = new Genre(name);
        child.setDescription(description);

        parent.addChild(child);
        genreRepository.save(parent);

        return child;
    }


    public String showGenreTree(Long id) {
        Genre genre = genreRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(GENRE_NOT_FOUND));
        return genre.show("");
    }

    public int countSubgenres(Long id) {
        Genre genre = genreRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(GENRE_NOT_FOUND));
        return genre.countSubgenres();
    }


    public List<GenreTreeDto> getAllGenresAsTree() {
        List<Genre> all = genreRepository.findAll();

        Set<Long> childIds = new HashSet<>();
        for (Genre g : all) {
            for (Genre child : g.getChildren()) {
                childIds.add(child.getId());
            }
        }

        List<GenreTreeDto> roots = new ArrayList<>();
        for (Genre g : all) {
            if (g.getParent() == null) {
                GenreTreeDto dto = buildGenreTreeDto(g);
                roots.add(dto);
            }
        }

        return roots;
    }

    private GenreTreeDto buildGenreTreeDto(Genre genre) {
        GenreTreeDto dto = new GenreTreeDto();
        dto.setId(genre.getId());
        dto.setName(genre.getName());
        dto.setDescription(genre.getDescription());

        List<GenreTreeDto> childDtos = new ArrayList<>();
        for (Genre child : genre.getChildren()) {
            childDtos.add(buildGenreTreeDto(child));
        }
        dto.setChildren(childDtos);

        return dto;
    }
}