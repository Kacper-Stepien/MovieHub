package com.example.movies_api.exception;

public class InvalidCredentialsException extends BadRequestException {
    public InvalidCredentialsException(String message) {
        super(message);
    }
}