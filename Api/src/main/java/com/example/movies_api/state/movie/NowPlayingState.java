package com.example.movies_api.state.movie;

public class NowPlayingState implements MovieState {
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
