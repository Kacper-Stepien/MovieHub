package com.example.movies_api.bad_practices;

public class ReviewRequest {
    private final User user;
    private final String movieTitle;
    private final int rating;
    private final String reviewText;
    private final boolean validate;
    private final boolean writeToFile;
    private final boolean sendEmail;
    private final String filename;
    private final int attemptNumber;

    public ReviewRequest(User user, String movieTitle, int rating, String reviewText,
            boolean validate, boolean writeToFile, boolean sendEmail,
            String filename, int attemptNumber) {
        this.user = user;
        this.movieTitle = movieTitle;
        this.rating = rating;
        this.reviewText = reviewText;
        this.validate = validate;
        this.writeToFile = writeToFile;
        this.sendEmail = sendEmail;
        this.filename = filename;
        this.attemptNumber = attemptNumber;
    }

    public User getUser() {
        return user;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public int getRating() {
        return rating;
    }

    public String getReviewText() {
        return reviewText;
    }

    public boolean isValidate() {
        return validate;
    }

    public boolean isWriteToFile() {
        return writeToFile;
    }

    public boolean isSendEmail() {
        return sendEmail;
    }

    public String getFilename() {
        return filename;
    }

    public int getAttemptNumber() {
        return attemptNumber;
    }
}
