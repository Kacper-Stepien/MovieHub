package com.example.movies_api.service;

import com.example.movies_api.constants.Messages;
import com.example.movies_api.dto.MovieDto;
import com.example.movies_api.dto.MovieGenresDto;
import com.example.movies_api.dto.MovieSaveDto;
import com.example.movies_api.dto.UpdateMovieDto;
import com.example.movies_api.exception.BadRequestException;
import com.example.movies_api.exception.ResourceNotFoundException;
import com.example.movies_api.factory.VideoFactory;
import com.example.movies_api.mapper.MovieDtoMapper;
import com.example.movies_api.memento.movie_memento.MovieListCaretaker;
import com.example.movies_api.memento.movie_memento.MovieListMemento;
import com.example.movies_api.model.Genre;
import com.example.movies_api.model.Movie;
import com.example.movies_api.movie_data_provider.ExternalMovieProvider;
import com.example.movies_api.movie_data_provider.LocalMovieProvider;
import com.example.movies_api.open_close.DefaultMovieRankingStrategy;
import com.example.movies_api.open_close.MovieRankingStrategy;
import com.example.movies_api.proxy.MovieDataProxy;
import com.example.movies_api.repository.GenreRepository;
import com.example.movies_api.repository.MovieRepository;
import com.example.movies_api.state.movie.ArchivedState;
import com.example.movies_api.state.movie.NowPlayingState;
import com.example.movies_api.state.movie.UpcomingState;
import com.example.movies_api.stats.StatsCollector;
import com.example.movies_api.storage.FileStorageService;
import com.example.movies_api.interpreter.movie_query.Context;
import com.example.movies_api.interpreter.movie_query.Expression;
import com.example.movies_api.interpreter.movie_query.QueryParser;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import com.example.movies_api.observer.recomendation.*;

import java.io.FileNotFoundException;
import java.io.UncheckedIOException;
import java.util.List;
import java.util.Optional;

import static com.example.movies_api.constants.Messages.MOVIE_NOT_FOUND;

//bridge for movie provider
@Service
//@RequiredArgsConstructor //observer 3/3 [commented line]
public class MovieService {
    private final MovieRepository movieRepository;
    private final GenreRepository genreRepository;
    private final MovieDtoMapper mapper;
    private final MovieRankingStrategy rankingStrategy = new DefaultMovieRankingStrategy();

    private final LocalMovieProvider localMovieProvider;
    private final ExternalMovieProvider externalMovieProvider;
    private final MovieDataProxy movieDataProxy;

    private final MovieListCaretaker movieListCaretaker;

    // observer 3/3 [added line]
    private final MovieObservable movieObservable = new MovieObservable();
    //private final MovieObservable movieObservable;


    //observer 3/3 [added observer over movie repository]
    public MovieService(MovieRepository movieRepository, GenreRepository genreRepository, MovieDtoMapper mapper, LocalMovieProvider localMovieProvider, ExternalMovieProvider externalMovieProvider, MovieDataProxy movieDataProxy, MovieListCaretaker movieListCaretaker) {
        this.movieRepository = movieRepository;
        this.genreRepository = genreRepository;
        this.mapper = mapper;
        this.localMovieProvider = localMovieProvider;
        this.externalMovieProvider = externalMovieProvider;
        this.movieDataProxy = movieDataProxy;
        this.movieListCaretaker = movieListCaretaker;
        this.movieObservable.addObserver(new GenreBasedRecommendationObserver(movieRepository));
    }

    // before using the proxy
    // public List<MovieDto> getMovies(String source) {
    //     if (source.equalsIgnoreCase("external")) {
    //         return externalMovieProvider.getMovies();
    //     }
    //     return localMovieProvider.getMovies();
    // }

    public List<MovieDto> getMovies(String source) {
        // Using the proxy to get movies
        if (source.equalsIgnoreCase("external")) {
            return movieDataProxy.getMoviesFromSource("external");
        }
        return movieDataProxy.getMoviesFromSource("local");
    }

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

        // 📦 Zapisz aktualny stan filmów
        movieListCaretaker.save(new MovieListMemento(movieRepository.findAll()));

        // before using video factory
        // Movie movie = new Movie();
        // movie.setTitle(movieToSave.getTitle());
        // movie.setOriginalTitle(movieToSave.getOriginalTitle());
        // movie.setPromoted(movieToSave.isPromoted());
        // movie.setReleaseYear(movieToSave.getReleaseYear());
        // movie.setShortDescription(movieToSave.getShortDescription());
        // movie.setDescription(movieToSave.getDescription());
        // movie.setYoutubeTrailerId(movieToSave.getYoutubeTrailerId());
        //

        // using video factory

        Movie movie = VideoFactory.createMovie(
                movieToSave.getTitle(),
                movieToSave.getOriginalTitle(),
                movieToSave.getShortDescription(),
                movieToSave.getDescription(),
                movieToSave.getYoutubeTrailerId(),
                movieToSave.getReleaseYear(),
                movieToSave.isPromoted(),
                "no_poster"
                );

        // Using State pattern to set the state of the movie
        assignMovieState(movie, movieToSave.getReleaseYear());

        double ranking = rankingStrategy.calculateRanking(movie);
        movie.setPromoted(ranking >= 7.0);

        Genre genre = genreRepository.findByNameIgnoreCase(movieToSave.getGenre())
                .orElseThrow(() -> new ResourceNotFoundException(Messages.GENRE_NOT_FOUND));
        movie.setGenre(genre);

        // Wykorzstanie Thread-safe Singleton z leniwą inicjalizacją (double-checked
        // locking). /////////////////////////
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

        //observer 3/3 [line added]
        movieObservable.notifyObservers(movie);


        return movieRepository.save(movie);
    }

    private void assignMovieState(Movie movie, int releaseYear) {
        int currentYear = java.time.Year.now().getValue();
        if (releaseYear == currentYear) {
            movie.setState(new NowPlayingState());
        } else if (releaseYear < currentYear) {
            movie.setState(new ArchivedState());
        } else {
            movie.setState(new UpcomingState());
        }
    }

    /*  //before adding memento that restores movie list to state before change
    public Movie addMovie(MovieSaveDto movieToSave) {
        if (movieRepository.findByTitle(movieToSave.getTitle()).isPresent()) {
            throw new BadRequestException(Messages.MOVIE_TITLE_EXISTS);
        }

        // before using video factory
        // Movie movie = new Movie();
        // movie.setTitle(movieToSave.getTitle());
        // movie.setOriginalTitle(movieToSave.getOriginalTitle());
        // movie.setPromoted(movieToSave.isPromoted());
        // movie.setReleaseYear(movieToSave.getReleaseYear());
        // movie.setShortDescription(movieToSave.getShortDescription());
        // movie.setDescription(movieToSave.getDescription());
        // movie.setYoutubeTrailerId(movieToSave.getYoutubeTrailerId());
        //

        // using video factory

        Movie movie = VideoFactory.createMovie(
                movieToSave.getTitle(),
                movieToSave.getOriginalTitle(),
                movieToSave.getShortDescription(),
                movieToSave.getDescription(),
                movieToSave.getYoutubeTrailerId(),
                movieToSave.getReleaseYear(),
                movieToSave.isPromoted(),
                "no_poster"
                );

        Genre genre = genreRepository.findByNameIgnoreCase(movieToSave.getGenre())
                .orElseThrow(() -> new ResourceNotFoundException(Messages.GENRE_NOT_FOUND));
        movie.setGenre(genre);

        // Wykorzstanie Thread-safe Singleton z leniwą inicjalizacją (double-checked
        // locking). /////////////////////////
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
    * */

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

        updateMovieDto.getReleaseYear().ifPresent(year -> assignMovieState(movie, year));

        movieRepository.save(movie);
    }

    @PreAuthorize("hasRole('ADMIN')")
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
    
    /**
     * Find movies using a query expression language (Interpreter pattern)
     * Example: "GENRE Action AND YEAR 2020"
     * 
     * @param queryExpression the query expression to evaluate
     * @return list of movies that match the query
     */
    public List<MovieDto> findMoviesByQueryExpression(String queryExpression) {
        // Get all movies for the context
        List<MovieDto> allMovies = findAllMovies();
        Context context = new Context(allMovies);
        
        // Parse the expression and interpret it
        QueryParser parser = new QueryParser();
        Expression expression = parser.parse(queryExpression);
        
        return expression.interpret(context);
    }


    // New method for restoring the movie list using memento pattern
    public void undoLastMovieAdd() {
        MovieListMemento memento = movieListCaretaker.getLastSnapshot();
        if (memento != null) {
            movieRepository.deleteAll();
            movieRepository.saveAll(memento.getSavedState());
        } else {
            throw new IllegalStateException("Brak zapisanej poprzedniej wersji listy filmów.");
        }
    }


}