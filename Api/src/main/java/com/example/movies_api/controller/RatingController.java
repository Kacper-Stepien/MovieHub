package com.example.movies_api.controller;

import com.example.movies_api.dto.RatingDto;
import com.example.movies_api.dto.RatingRequestDto;
import com.example.movies_api.exception.ResourceNotFoundException;
import com.example.movies_api.model.User;
import com.example.movies_api.repository.UserRepository;
import com.example.movies_api.service.RatingService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/ratings")
public class RatingController {
    private final RatingService ratingService;
    private final UserRepository userRepository;

    @PostMapping("/rate-movie")
    public ResponseEntity<RatingDto> addRating(@Valid @RequestBody RatingRequestDto ratingRequest) {
        String currentUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        RatingDto savedRating = ratingService.addOrUpdateRating(
                currentUserEmail,
                ratingRequest.getMovieId(),
                ratingRequest.getRating()
        );

        URI savedRatingUri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedRating.getId())
                .toUri();
        return ResponseEntity.created(savedRatingUri).body(savedRating);
    }

    @DeleteMapping("/delete-rating/{ratingId}")
    public ResponseEntity<Void> deleteRating(@PathVariable Long ratingId) {
        String currentUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(currentUserEmail)
                .orElseThrow(() -> new ResourceNotFoundException("Nie znaleziono użytkownika"));

        ratingService.deleteRatingByIdAndUserId(ratingId, user.getId());

        return ResponseEntity.noContent().build();
    }

}