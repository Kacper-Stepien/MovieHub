package com.example.movies_api.bad_practices;

public class ValidationService {
    public boolean validateUser(User user) {
        return user.getUsername().length() >= 3 &&
                user.getEmail().contains("@") &&
                user.getPassword().length() >= 5;
    }
}
