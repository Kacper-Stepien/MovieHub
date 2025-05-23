package com.example.movies_api.dto;

import lombok.Getter;
import lombok.Setter;

import jakarta.validation.constraints.NotNull;

@Getter
@Setter
public class RatingDto {
    private Long id;
    @NotNull
    private Integer rating;
}
