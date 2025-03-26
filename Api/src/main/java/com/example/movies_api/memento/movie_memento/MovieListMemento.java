package com.example.movies_api.memento.movie_memento;
import com.example.movies_api.model.Movie;
import java.util.List;

public class MovieListMemento {
    private final List<Movie> moviesSnapshot;

    public MovieListMemento(List<Movie> moviesSnapshot) {
        this.moviesSnapshot = moviesSnapshot;
    }

    public List<Movie> getSavedState() {
        return moviesSnapshot;
    }
}