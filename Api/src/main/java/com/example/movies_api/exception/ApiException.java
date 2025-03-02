package com.example.movies_api.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.Map;

@AllArgsConstructor
@Getter
public class ApiException {
    private final String message;
    private final HttpStatus httpStatus;
    private final Map<String, String> errors;

    public ApiException(String message, HttpStatus httpStatus) {
        this.message = message;
        this.httpStatus = httpStatus;
        this.errors = null;
    }
}
