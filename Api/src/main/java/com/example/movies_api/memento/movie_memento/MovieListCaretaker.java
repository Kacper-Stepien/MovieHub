package com.example.movies_api.memento.movie_memento;
import org.springframework.stereotype.Component;


//1/3 memento pattern - for rolling back to movie list from before adding a movie
//[previously] that file did not exist
@Component
public class MovieListCaretaker {
    private MovieListMemento lastSnapshot;

    public void save(MovieListMemento memento) {
        this.lastSnapshot = memento;
    }

    public MovieListMemento getLastSnapshot() {
        return lastSnapshot;
    }
}