package com.example.movies_api.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RatingRequestDto {
    private long movieId;

    @Min(1)
    @Max(5)
    private int rating;
}