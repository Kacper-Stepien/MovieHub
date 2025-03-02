package com.example.movies_api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UpdatePasswordDto {
    @NotBlank(message = "Stare hasło nie może być puste.")
    private String oldPassword;

    @NotBlank(message = "Nowe hasło nie może być puste.")
    @Size(min = 8, message = "Hasło musi zawierać co najmniej 8 znaków")
    private String newPassword;

    @NotBlank(message = "Potwierdzenie hasła nie może być puste.")
    @Size(min = 8, message = "Hasło musi zawierać co najmniej 8 znaków")
    private String confirmPassword;
}