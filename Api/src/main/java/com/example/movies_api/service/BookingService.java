package com.example.movies_api.service;

import com.example.movies_api.exception.ResourceNotFoundException;
import com.example.movies_api.model.Booking;
import com.example.movies_api.model.Movie;
import com.example.movies_api.model.User;
import com.example.movies_api.repository.BookingRepository;
import com.example.movies_api.repository.MovieRepository;
import com.example.movies_api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.movies_api.constants.Messages.MOVIE_NOT_FOUND;
import static com.example.movies_api.constants.Messages.USER_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;
    private final MovieRepository movieRepository;
    private final UserRepository userRepository;

    public Booking createBooking(Long movieId, String userEmail) {
        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new ResourceNotFoundException(MOVIE_NOT_FOUND));

        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new ResourceNotFoundException(USER_NOT_FOUND));

        Booking booking = new Booking(movie, user);
        return bookingRepository.save(booking);
    }

    public Booking confirmBooking(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException("Rezerwacja nie znaleziona"));

        booking.confirm();
        return bookingRepository.save(booking);
    }

    public Booking cancelBooking(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException("Rezerwacja nie znaleziona"));

        booking.cancel();
        return bookingRepository.save(booking);
    }

    public List<Booking> getUserBookings(String email) {
        return bookingRepository.findByUser_Email(email);
    }

    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }
}
