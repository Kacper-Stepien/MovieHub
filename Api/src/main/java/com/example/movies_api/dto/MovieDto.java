package com.example.movies_api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class MovieDto {
    private Long id;
    @NotBlank
    private String title;
    @NotBlank
    private String originalTitle;
    @NotBlank
    private String shortDescription;
    @NotBlank
    private String description;
    @NotBlank
    private String youtubeTrailerId;
    @NotNull
    private Integer releaseYear;
    @NotBlank
    private String genre;
    private boolean promoted;
    @NotBlank
    private String poster;
    private double avgRating;
    private int ratingCount;
    private String movieType;
    private String availabilityMessage;
}

