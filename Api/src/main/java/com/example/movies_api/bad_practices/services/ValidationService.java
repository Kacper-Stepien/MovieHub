package com.example.movies_api.bad_practices.services;

import com.example.movies_api.bad_practices.exceptions.InvalidUserDataException;
import com.example.movies_api.bad_practices.model.User;

public class ValidationService {
    public void validateUser(User user) {
        if (user.getUsername().length() < 3 ||
                !user.getEmail().contains("@") ||
                user.getPassword().length() < 5) {
            throw new InvalidUserDataException("Dane użytkownika są nieprawidłowe.");
        }
    }
}
