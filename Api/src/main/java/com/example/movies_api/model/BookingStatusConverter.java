package com.example.movies_api.model;

import com.example.movies_api.state.booking.*;

public class BookingStatusConverter {

    public static BookingState toState(String status) {
        return switch (status) {
            case "Zarezerwowano" -> new ReservedState();
            case "Potwierdzona" -> new ConfirmedState();
            case "Anulowana" -> new CancelledState();
            default -> new ReservedState(); // domyślnie
        };
    }
}
