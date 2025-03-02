package com.example.movies_api.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    @NotBlank(message = "Imię nie może być puste")
    private String firstName;
    @NotBlank(message = "Nazwisko nie może być puste")
    private String lastName;
    @Email(message = "Podaj poprawny adres Email")
    private String email;
    @NotBlank(message = "Hasło nie może być puste")
    @Size(min = 8, message = "Hasło musi zawierać co najmniej 8 znaków")
    private String password;
}

