package com.example.movies_api.service;

import com.example.movies_api.exception.ApiException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

// Singleton ///////////////////////////////////////////////////////////////////////////////////////////////////////////
public class ValidationService {

    private static ValidationService instance;

    private ValidationService() { }

    public static ValidationService getInstance() {
        if (instance == null) {
            instance = new ValidationService();
        }
        return instance;
    }

    public ResponseEntity<?> validate(BindingResult result) {
        if (result.hasErrors()) {
            Map<String, String> errorsMap = new HashMap<>();
            for (FieldError error : result.getFieldErrors()) {
                errorsMap.put(error.getField(), error.getDefaultMessage());
            }

            ApiException apiException = new ApiException(
                    "Nieprawidłowe dane",
                    BAD_REQUEST,
                    errorsMap
            );

            return new ResponseEntity<>(apiException, BAD_REQUEST);
        }
        return null;
    }
}
