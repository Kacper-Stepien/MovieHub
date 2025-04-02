package com.example.movies_api.memento.trailer_memento;
import org.springframework.stereotype.Component;


//2/3 memento pattern - for rolling back to trailer list from before adding a movie
//[previously] that file did not exist
@Component
public class TrailerListCaretaker {
    private TrailerListMemento lastSnapshot;

    public void save(TrailerListMemento memento) {
        this.lastSnapshot = memento;
    }

    public TrailerListMemento getLastSnapshot() {
        return lastSnapshot;
    }
}