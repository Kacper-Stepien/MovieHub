package com.example.movies_api.bad_practices;

public class EmailService {
    public void sendReviewConfirmation(String email, String movieTitle) {
        System.out.println("Wysyłam e-mail do " + email + ": Dziękujemy za recenzję \"" + movieTitle + "\"");
    }
}
