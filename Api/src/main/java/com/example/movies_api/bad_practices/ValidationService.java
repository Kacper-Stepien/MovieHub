package com.example.movies_api.bad_practices;

public class ValidationService {
    public void validateUser(User user) {
        if (user.getUsername().length() < 3 ||
                !user.getEmail().contains("@") ||
                user.getPassword().length() < 5) {
            throw new InvalidUserDataException("Dane użytkownika są nieprawidłowe.");
        }
    }
}
