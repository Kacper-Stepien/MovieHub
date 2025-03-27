package com.example.movies_api.state;

public class ArchivedState implements MovieState {
    @Override
    public String getAvailabilityMessage() {
        return "Film został usunięty z repertuaru.";
    }

    @Override
    public boolean canBeBooked() {
        return false;
    }

    @Override
    public boolean canBeRated() {
        return true; // Można oceniać, mimo że nie ma go w kinach
    }
}
