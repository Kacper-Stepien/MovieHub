package com.example.movies_api.repository;

import com.example.movies_api.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByUser_Email(String email);

    List<Booking> findByMovie_Id(Long movieId);
}
