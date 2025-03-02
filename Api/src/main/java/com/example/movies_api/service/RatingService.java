package com.example.movies_api.service;

import com.example.movies_api.dto.RatingDto;
import com.example.movies_api.exception.BadRequestException;
import com.example.movies_api.exception.ResourceNotFoundException;
import com.example.movies_api.mapper.RatingDtoMapper;
import com.example.movies_api.model.Movie;
import com.example.movies_api.model.Rating;
import com.example.movies_api.model.User;
import com.example.movies_api.repository.MovieRepository;
import com.example.movies_api.repository.RatingRepository;
import com.example.movies_api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.example.movies_api.constants.Messages.*;

@Service
@RequiredArgsConstructor
public class RatingService {
    private final RatingRepository ratingRepository;
    private final UserRepository userRepository;
    private final MovieRepository movieRepository;

    public RatingDto addOrUpdateRating(String userEmail, long movieId, int rating) {
        Rating ratingToSaveOrUpdate = ratingRepository.findByUser_EmailAndMovie_Id(userEmail, movieId)
                .orElseGet(Rating::new);
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new ResourceNotFoundException(USER_NOT_FOUND));
        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new ResourceNotFoundException(MOVIE_NOT_FOUND));
        ratingToSaveOrUpdate.setUser(user);
        ratingToSaveOrUpdate.setMovie(movie);
        ratingToSaveOrUpdate.setRating(rating);
        ratingRepository.save(ratingToSaveOrUpdate);
        return RatingDtoMapper.map(ratingToSaveOrUpdate);
    }

    public void deleteRatingByIdAndUserId(Long ratingId, Long userId) {
        Rating rating = ratingRepository.findById(ratingId)
                .orElseThrow(() -> new ResourceNotFoundException(RATING_NOT_FOUND));

        if (!rating.getUser().getId().equals(userId)) {
            throw new BadRequestException(NO_PERMISSIONS);
        }

        ratingRepository.delete(rating);
    }
}