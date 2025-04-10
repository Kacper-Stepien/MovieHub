package com.example.movies_api.state.movie;

public class NowPlayingState implements Available, Bookable, Rateable {
    @Override
    public String getAvailabilityMessage() {
        return "Film jest aktualnie grany w kinach.";
    }

    @Override
    public boolean canBeBooked() {
        return true;
    }

    @Override
    public boolean canBeRated() {
        return true;
    }
}
