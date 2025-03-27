package com.example.movies_api.state.movie;

public interface MovieState {
    String getAvailabilityMessage();

    boolean canBeBooked();

    boolean canBeRated();
}
