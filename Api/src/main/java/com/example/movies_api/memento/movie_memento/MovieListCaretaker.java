package com.example.movies_api.memento.movie_memento;
import org.springframework.stereotype.Component;

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