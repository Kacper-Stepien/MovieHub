package com.example.movies_api.state.booking;

import com.example.movies_api.model.Booking;

public interface BookingState {
    void confirm(Booking context);

    void cancel(Booking context);

    String getStatusName();

    String notifyUser();
}
