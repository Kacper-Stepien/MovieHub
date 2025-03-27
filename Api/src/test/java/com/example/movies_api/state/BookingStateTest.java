package com.example.movies_api.state;

import com.example.movies_api.model.Booking;
import com.example.movies_api.model.Movie;
import com.example.movies_api.model.User;
import com.example.movies_api.state.booking.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BookingStateTest {

    private Booking booking;

    @BeforeEach
    void setup() {
        Movie dummyMovie = new Movie();
        dummyMovie.setTitle("Testowy film");

        User dummyUser = new User();
        dummyUser.setEmail("test@example.com");

        booking = new Booking(dummyMovie, dummyUser);
    }

    @Test
    void bookingHasDefaultReservedState() {
        assertNotNull(booking.getState());
        assertTrue(booking.getState() instanceof ReservedState);
        assertEquals("Zarezerwowano", booking.getStatus());
        assertEquals("Twoja rezerwacja została zarejestrowana. Oczekuje na potwierdzenie.", booking.notifyUser());
    }

    @Test
    void confirmChangesStateToConfirmed() {
        booking.confirm();

        assertTrue(booking.getState() instanceof ConfirmedState);
        assertEquals("Potwierdzona", booking.getStatus());
        assertEquals("Twoja rezerwacja została potwierdzona!", booking.notifyUser());
    }

    @Test
    void cancelChangesStateToCancelled() {
        booking.cancel();

        assertTrue(booking.getState() instanceof CancelledState);
        assertEquals("Anulowana", booking.getStatus());
        assertEquals("Twoja rezerwacja została anulowana.", booking.notifyUser());
    }

    @Test
    void confirmThenCancelShouldEndInCancelledState() {
        booking.confirm();
        booking.cancel();

        assertTrue(booking.getState() instanceof CancelledState);
        assertEquals("Anulowana", booking.getStatus());
    }

    @Test
    void cancelledBookingDoesNotChangeOnConfirm() {
        booking.cancel();
        booking.confirm(); // powinno zostać anulowane

        assertTrue(booking.getState() instanceof CancelledState);
        assertEquals("Anulowana", booking.getStatus());
    }
}
