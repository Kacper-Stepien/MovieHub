package com.example.movies_api.state;

import org.junit.jupiter.api.Test;

import com.example.movies_api.state.movie.ArchivedState;
import com.example.movies_api.state.movie.MovieState;
import com.example.movies_api.state.movie.NowPlayingState;
import com.example.movies_api.state.movie.UpcomingState;

import static org.junit.jupiter.api.Assertions.*;

class MovieStateTest {

    @Test
    void nowPlayingState_allowsBookingAndRating() {
        MovieState state = new NowPlayingState();

        assertTrue(state.canBeBooked());
        assertTrue(state.canBeRated());
        assertEquals("Film jest aktualnie grany w kinach.", state.getAvailabilityMessage());
    }

    @Test
    void upcomingState_disallowsBookingAndRating() {
        MovieState state = new UpcomingState();

        assertFalse(state.canBeBooked());
        assertFalse(state.canBeRated());
        assertEquals("Film wkrótce pojawi się w repertuarze.", state.getAvailabilityMessage());
    }

    @Test
    void archivedState_allowsRatingButNotBooking() {
        MovieState state = new ArchivedState();

        assertFalse(state.canBeBooked());
        assertTrue(state.canBeRated());
        assertEquals("Film został usunięty z repertuaru.", state.getAvailabilityMessage());
    }

    @Test
    void stateCanBeChangedInMovie() {
        var movie = new com.example.movies_api.model.Movie();
        movie.setState(new NowPlayingState());

        assertTrue(movie.canBeBooked());
        assertEquals("Film jest aktualnie grany w kinach.", movie.getAvailabilityMessage());

        movie.setState(new ArchivedState());

        assertFalse(movie.canBeBooked());
        assertEquals("Film został usunięty z repertuaru.", movie.getAvailabilityMessage());
    }

    @Test
    void movieHasDefaultState() {
        var movie = new com.example.movies_api.model.Movie();
        assertNotNull(movie.getState());
        assertFalse(movie.canBeBooked()); // domyślnie UpcomingState
        assertEquals("Film wkrótce pojawi się w repertuarze.", movie.getAvailabilityMessage());
    }

}
