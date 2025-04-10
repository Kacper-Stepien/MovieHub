package com.example.movies_api.bad_practices;

import java.util.ArrayList;
import java.util.List;

public class ReviewService {
    private final List<String> reviews = new ArrayList<>();

    public String createReview(String movieTitle, int rating, String reviewText, String author) {
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
