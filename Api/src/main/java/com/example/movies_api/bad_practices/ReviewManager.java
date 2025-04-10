package com.example.movies_api.bad_practices;

public class ReviewManager {
    private final UserService userService = new UserService();
    private final ValidationService validationService = new ValidationService();
    private final ReviewService reviewService = new ReviewService();
    private final FileWriterService fileWriterService = new FileWriterService();
    private final EmailService emailService = new EmailService();

    public void submitReview(User user, String movieTitle, int rating, String reviewText,
            boolean validate, boolean writeToFile, boolean sendEmail,
            String filename, int attemptNumber) {

        if (validate && !validationService.validateUser(user)) {
            System.out.println("Nieprawidłowe dane!");
            return;
        }

        userService.registerUser(user);
        String review = reviewService.createReview(movieTitle, rating, reviewText, user.getUsername());

        if (writeToFile) {
            fileWriterService.writeToFile(review, filename);
        }

        if (sendEmail) {
            emailService.sendReviewConfirmation(user.getEmail(), movieTitle);
        }

        System.out.println("Liczba prób: " + attemptNumber);
        if (attemptNumber > 100) {
            System.out.println("Zbyt wiele prób. Blokada.");
        }
    }

    public void emergencyPurge() {
        userService.clearAllUsers();
        reviewService.clearReviews();
        System.out.println("Dane użytkowników i recenzji zostały wyczyszczone.");
    }
}
