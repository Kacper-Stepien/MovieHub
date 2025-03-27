package com.example.movies_api.state.booking;

import com.example.movies_api.model.Booking;

public class ConfirmedState implements BookingState {

    @Override
    public void confirm(Booking context) {
        // Już potwierdzona — nic nie rób
    }

    @Override
    public void cancel(Booking context) {
        context.setState(new CancelledState());
    }

    @Override
    public String getStatusName() {
        return "Potwierdzona";
    }

    @Override
    public String notifyUser() {
        return "Twoja rezerwacja została potwierdzona!";
    }
}
