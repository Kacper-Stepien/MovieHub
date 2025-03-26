package com.example.movies_api.memento.trailer_memento;
import org.springframework.stereotype.Component;

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