package com.example.movies_api.dto;

import lombok.Data;

import java.util.List;

@Data
public class MovieCreationRequest {
    private MovieSaveDto movieDto;
    private RatingRequestDto ratingDto;
}