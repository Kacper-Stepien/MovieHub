package com.example.movies_api.state.movie;

public class UpcomingState implements Available, Bookable, Rateable{
    @Override
    public String getAvailabilityMessage() {
        return "Film wkrótce pojawi się w repertuarze.";
    }

    @Override
    public boolean canBeBooked() {
        return false;
    }

    @Override
    public boolean canBeRated() {
        return false;
    }
}