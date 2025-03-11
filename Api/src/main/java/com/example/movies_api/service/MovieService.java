package com.example.movies_api.service;

import com.example.movies_api.constants.Messages;
import com.example.movies_api.dto.MovieDto;
import com.example.movies_api.dto.MovieGenresDto;
import com.example.movies_api.dto.MovieSaveDto;
import com.example.movies_api.dto.UpdateMovieDto;
import com.example.movies_api.exception.BadRequestException;
import com.example.movies_api.exception.ResourceNotFoundException;
import com.example.movies_api.mapper.MovieDtoMapper;
import com.example.movies_api.model.Genre;
import com.example.movies_api.model.Movie;
import com.example.movies_api.repository.GenreRepository;
import com.example.movies_api.repository.MovieRepository;
import com.example.movies_api.storage.FileStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.UncheckedIOException;
import java.util.List;
import java.util.Optional;

import static com.example.movies_api.constants.Messages.MOVIE_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class MovieService {
    private final MovieRepository movieRepository;
    private final GenreRepository genreRepository;
//    private final FileStorageService fileStorageService;
    private final MovieDtoMapper mapper;

    public List<MovieDto> findAllMovies() {
        return movieRepository.findAll().stream()
                .map(MovieDtoMapper::map)
                .toList();
    }

    public List<MovieDto> findAllPromotedMovies() {
        return movieRepository.findAllByPromotedIsTrue().stream()
                .map(MovieDtoMapper::map)
                .toList();
    }

    public MovieDto findMovieById(long id) {
        return movieRepository.findById(id).map(MovieDtoMapper::map)
                .orElseThrow(() -> new ResourceNotFoundException(MOVIE_NOT_FOUND));
    }

    public MovieGenresDto findMovieDtoById(long id) {
        return movieRepository.findById(id).map(MovieDtoMapper::mapToDto)
                .orElseThrow(() -> new ResourceNotFoundException(MOVIE_NOT_FOUND));
    }

    public List<MovieDto> findMoviesByGenreName(String genre) {
        return movieRepository.findAllByGenre_NameIgnoreCase(genre).stream()
                .map(MovieDtoMapper::map)
                .toList();
    }

    public Movie addMovie(MovieSaveDto movieToSave) {
        if (movieRepository.findByTitle(movieToSave.getTitle()).isPresent()) {
            throw new BadRequestException(Messages.MOVIE_TITLE_EXISTS);
        }
        Movie movie = new Movie();
        movie.setTitle(movieToSave.getTitle());
        movie.setOriginalTitle(movieToSave.getOriginalTitle());
        movie.setPromoted(movieToSave.isPromoted());
        movie.setReleaseYear(movieToSave.getReleaseYear());
        movie.setShortDescription(movieToSave.getShortDescription());
        movie.setDescription(movieToSave.getDescription());
        movie.setYoutubeTrailerId(movieToSave.getYoutubeTrailerId());

        Genre genre = genreRepository.findByNameIgnoreCase(movieToSave.getGenre())
                .orElseThrow(() -> new ResourceNotFoundException(Messages.GENRE_NOT_FOUND));
        movie.setGenre(genre);

        // Wykorzstanie Thread-safe Singleton z leniwą inicjalizacją (double-checked locking). /////////////////////////
        if (movieToSave.getPoster() != null) {
            try {
                FileStorageService storageService = FileStorageService.getInstance();
                String savedFileName = storageService.saveImage(movieToSave.getPoster());
                movie.setPoster(savedFileName);
            }
            catch(FileNotFoundException e) {
                throw new UncheckedIOException(e);
            }
        }
        return movieRepository.save(movie);
    }

    public List<MovieDto> findTopMovies(int size) {
        Pageable page = Pageable.ofSize(size);
        return movieRepository.findTopByRating(page).stream()
                .map(MovieDtoMapper::map)
                .toList();
    }

    public void updateMovie(Long id, UpdateMovieDto updateMovieDto) {
        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(MOVIE_NOT_FOUND));

        updateMovieDto.getTitle().ifPresent(movie::setTitle);
        updateMovieDto.getOriginalTitle().ifPresent(movie::setOriginalTitle);
        updateMovieDto.getShortDescription().ifPresent(movie::setShortDescription);
        updateMovieDto.getYoutubeTrailerId().ifPresent(movie::setYoutubeTrailerId);
        updateMovieDto.getReleaseYear().ifPresent(movie::setReleaseYear);

        movieRepository.save(movie);
    }

    public void deleteMovie(long id) {
        if (!movieRepository.existsById(id)) {
            throw new ResourceNotFoundException(MOVIE_NOT_FOUND);
        }
        movieRepository.deleteById(id);
    }

    public List<MovieDto> findAllWithFilters(String genre, Integer releaseYear, int page) {
        Pageable size = PageRequest.of(page, 10);
        return movieRepository.findAllByGenre_NameAndReleaseYear(genre, releaseYear, size).stream()
                .map(MovieDtoMapper::map)
                .toList();
    }
}
