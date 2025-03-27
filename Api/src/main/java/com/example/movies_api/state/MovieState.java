package com.example.movies_api.state;

public interface MovieState {
    String getAvailabilityMessage();

    boolean canBeBooked();

    boolean canBeRated();
}
