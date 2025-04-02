package com.example.movies_api.memento.movie_memento;
import com.example.movies_api.model.Movie;
import java.util.List;


//1/3 memento pattern - for rolling back to movie list from before adding a movie
//[previously] that file did not exist
public class MovieListMemento {
    private final List<Movie> moviesSnapshot;

    public MovieListMemento(List<Movie> moviesSnapshot) {
        this.moviesSnapshot = moviesSnapshot;
    }

    public List<Movie> getSavedState() {
        return moviesSnapshot;
    }
}