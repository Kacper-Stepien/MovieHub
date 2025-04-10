package com.example.movies_api.bad_practices.services;

import java.util.ArrayList;
import java.util.List;
import com.example.movies_api.bad_practices.model.ReviewRequest;

public class ReviewService {
    private final List<String> reviews = new ArrayList<>();

    public String createReview(ReviewRequest request) {
        String movieTitle = request.getMovieTitle();
        int rating = request.getRating();
        String reviewText = request.getReviewText();
        String author = request.getUser() != null ? request.getUser().getUsername() : "Anonymous";
        
        String review = "Film: " + movieTitle + ", Ocena: " + rating + ", Recenzja: " + reviewText + ", Autor: "
                + author;
        reviews.add(review);
        return review;
    }

    public List<String> getAllReviews() {
        return reviews;
    }

    public void clearReviews() {
        reviews.clear();
    }
}
