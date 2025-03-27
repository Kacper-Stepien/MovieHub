package com.example.movies_api.state.booking;

import com.example.movies_api.model.Booking;

public class ReservedState implements BookingState {

    @Override
    public void confirm(Booking context) {
        context.setState(new ConfirmedState());
    }

    @Override
    public void cancel(Booking context) {
        context.setState(new CancelledState());
    }

    @Override
    public String getStatusName() {
        return "Zarezerwowano";
    }

    @Override
    public String notifyUser() {
        return "Twoja rezerwacja została zarejestrowana. Oczekuje na potwierdzenie.";
    }
}
