package com.example.movies_api.memento.trailer_memento;
import com.example.movies_api.model.Trailer;
import java.util.List;

public class TrailerListMemento {
    private final List<Trailer> trailersSnapshot;

    public TrailerListMemento(List<Trailer> trailersSnapshot) {
        this.trailersSnapshot = trailersSnapshot;
    }

    public List<Trailer> getSavedState() {
        return trailersSnapshot;
    }
}