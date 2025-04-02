package com.example.movies_api.observer.recomendation;

import com.example.movies_api.model.Movie;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

//observer 3/3 added file
@Component
public class MovieObservable {
    private List<MovieRecommendationObserver> observers = new ArrayList<>();

    public void addObserver(MovieRecommendationObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(MovieRecommendationObserver observer) {
        observers.remove(observer);
    }

    public void notifyObservers(Movie movie) {
        for (MovieRecommendationObserver observer : observers) {
            observer.update(movie);
        }
    }
}
