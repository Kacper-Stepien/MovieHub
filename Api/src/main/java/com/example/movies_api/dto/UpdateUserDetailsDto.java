package com.example.movies_api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpdateUserDetailsDto {
    @NotBlank(message = "Imię nie może być puste.")
    private String firstName;

    @NotBlank(message = "Nazwisko nie może być puste.")
    private String lastName;

    @NotBlank(message = "Email nie może być pusty.")
    @Email(message = "Niepoprawny format adresu email.")
    private String email;
}