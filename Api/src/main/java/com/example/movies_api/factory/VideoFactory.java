package com.example.movies_api.factory;

import com.example.movies_api.model.Movie;
import com.example.movies_api.model.Trailer;
import com.example.movies_api.factory.Video;

public class VideoFactory {
    public static Movie createMovie(
            String title, String originalTitle, String shortDescription, String description,
            String youtubeTrailerId, int releaseYear, boolean promoted, String poster) {
        return Movie.builder()
                .title(title)
                .originalTitle(originalTitle)
                .shortDescription(shortDescription)
                .description(description)
                .youtubeTrailerId(youtubeTrailerId)
                .releaseYear(releaseYear)
                .promoted(promoted)
                .poster(poster)
                .build();
    }

    public static Trailer createTrailer(String title, String description, String youtubeId, String thumbnail) {
        return Trailer.builder()
                .title(title)
                .description(description)
                .youtubeTrailerId(youtubeId)
                .thumbnail(thumbnail)
                .build();
    }
}


//used in files: MovieService.java, MovieDtoMapper.java, TrailerService.java
//for implementation of Factory several additional files were added: TrailerController, TrailerService, TrailerDto, TrailerRepository

