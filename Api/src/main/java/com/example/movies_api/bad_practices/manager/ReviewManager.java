package com.example.movies_api.bad_practices.manager;

import com.example.movies_api.bad_practices.exceptions.TooManyAttemptsException;
import com.example.movies_api.bad_practices.model.ReviewRequest;
import com.example.movies_api.bad_practices.services.EmailService;
import com.example.movies_api.bad_practices.services.FileWriterService;
import com.example.movies_api.bad_practices.services.ReviewService;
import com.example.movies_api.bad_practices.services.UserService;
import com.example.movies_api.bad_practices.services.ValidationService;

// public class ReviewManager {
//     private final UserService userService = new UserService();
//     private final ValidationService validationService = new ValidationService();
//     private final ReviewService reviewService = new ReviewService();
//     private final FileWriterService fileWriterService = new FileWriterService();
//     private final EmailService emailService = new EmailService();

//     public void submitReview(User user, String movieTitle, int rating, String reviewText,
//             boolean validate, boolean writeToFile, boolean sendEmail,
//             String filename, int attemptNumber) {

//         if (validate && !validationService.validateUser(user)) {
//             System.out.println("Nieprawidłowe dane!");
//             return;
//         }

//         userService.registerUser(user);
//         String review = reviewService.createReview(movieTitle, rating, reviewText, user.getUsername());

//         if (writeToFile) {
//             fileWriterService.writeToFile(review, filename);
//         }

//         if (sendEmail) {
//             emailService.sendReviewConfirmation(user.getEmail(), movieTitle);
//         }

//         System.out.println("Liczba prób: " + attemptNumber);
//         if (attemptNumber > 100) {
//             System.out.println("Zbyt wiele prób. Blokada.");
//         }
//     }

//     public void emergencyPurge() {
//         userService.clearAllUsers();
//         reviewService.clearReviews();
//         System.out.println("Dane użytkowników i recenzji zostały wyczyszczone.");
//     }
// }

public class ReviewManager {
    private static final int MAX_ATTEMPTS = 100;

    private final UserService userService = new UserService();
    private final ValidationService validationService = new ValidationService();
    private final ReviewService reviewService = new ReviewService();
    private final FileWriterService fileWriterService = new FileWriterService();
    private final EmailService emailService = new EmailService();

    public void submitReview(ReviewRequest request) {
        if (request.isValidate()) {
            validationService.validateUser(request.getUser());
        }

        userService.registerUser(request.getUser());

        String review = reviewService.createReview(request);

        if (request.isWriteToFile()) {
            fileWriterService.writeToFile(review, request.getFilename());
        }

        if (request.isSendEmail()) {
            emailService.sendReviewConfirmation(request.getUser().getEmail(), request.getMovieTitle());
        }

        if (request.getAttemptNumber() > MAX_ATTEMPTS) {
            throw new TooManyAttemptsException("Zbyt wiele prób. Użytkownik został zablokowany.");
        }

        System.out.println("Liczba prób: " + request.getAttemptNumber());
    }

    public void emergencyPurge() {
        userService.clearAllUsers();
        reviewService.clearReviews();
        System.out.println("Dane użytkowników i recenzji zostały wyczyszczone.");
    }
}
