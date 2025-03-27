package com.example.movies_api.state.booking;

import com.example.movies_api.model.Booking;

public class CancelledState implements BookingState {

    @Override
    public void confirm(Booking context) {
        // Nie można potwierdzić anulowanej rezerwacji
    }

    @Override
    public void cancel(Booking context) {
        // Już anulowana
    }

    @Override
    public String getStatusName() {
        return "Anulowana";
    }

    @Override
    public String notifyUser() {
        return "Twoja rezerwacja została anulowana.";
    }
}
