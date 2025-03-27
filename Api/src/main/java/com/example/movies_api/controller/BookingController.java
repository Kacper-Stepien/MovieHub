package com.example.movies_api.controller;

import com.example.movies_api.model.Booking;
import com.example.movies_api.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    @PostMapping("/create")
    public ResponseEntity<Booking> createBooking(@RequestParam Long movieId, @RequestParam String userEmail) {
        Booking booking = bookingService.createBooking(movieId, userEmail);
        return ResponseEntity.ok(booking);
    }

    @PostMapping("/{id}/confirm")
    public ResponseEntity<Booking> confirmBooking(@PathVariable Long id) {
        Booking updated = bookingService.confirmBooking(id);
        return ResponseEntity.ok(updated);
    }

    @PostMapping("/{id}/cancel")
    public ResponseEntity<Booking> cancelBooking(@PathVariable Long id) {
        Booking updated = bookingService.cancelBooking(id);
        return ResponseEntity.ok(updated);
    }

    @GetMapping("/user")
    public ResponseEntity<List<Booking>> getUserBookings(@RequestParam String email) {
        return ResponseEntity.ok(bookingService.getUserBookings(email));
    }

    @GetMapping("/all")
    public ResponseEntity<List<Booking>> getAll() {
        return ResponseEntity.ok(bookingService.getAllBookings());
    }
}
