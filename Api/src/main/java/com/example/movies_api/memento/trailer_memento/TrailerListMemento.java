package com.example.movies_api.memento.trailer_memento;
import com.example.movies_api.model.Trailer;
import java.util.List;

//2/3 memento pattern - for rolling back to trailer list from before adding a movie
//[previously] that file did not exist
public class TrailerListMemento {
    private final List<Trailer> trailersSnapshot;

    public TrailerListMemento(List<Trailer> trailersSnapshot) {
        this.trailersSnapshot = trailersSnapshot;
    }

    public List<Trailer> getSavedState() {
        return trailersSnapshot;
    }
}