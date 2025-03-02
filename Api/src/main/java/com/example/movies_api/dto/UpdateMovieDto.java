package com.example.movies_api.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Optional;



@JsonInclude(JsonInclude.Include.NON_ABSENT)
public class UpdateMovieDto {
    private Optional<String> title = Optional.empty();
    private Optional<String> originalTitle = Optional.empty();
    private Optional<String> shortDescription = Optional.empty();
    private Optional<String> youtubeTrailerId = Optional.empty();
    private Optional<Integer> releaseYear = Optional.empty();

    public Optional<String> getTitle() {
        return title;
    }

    public void setTitle(Optional<String> title) {
        this.title = title;
    }

    public Optional<String> getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(Optional<String> originalTitle) {
        this.originalTitle = originalTitle;
    }

    public Optional<String> getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(Optional<String> shortDescription) {
        this.shortDescription = shortDescription;
    }

    public Optional<String> getYoutubeTrailerId() {
        return youtubeTrailerId;
    }

    public void setYoutubeTrailerId(Optional<String> youtubeTrailerId) {
        this.youtubeTrailerId = youtubeTrailerId;
    }

    public Optional<Integer> getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(Optional<Integer> releaseYear) {
        this.releaseYear = releaseYear;
    }
}
