package com.example.movies_api.movie_data_provider;

import com.example.movies_api.dto.MovieDto;
import com.example.movies_api.model.Movie;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
@Qualifier("externalMovieProvider")
public class ExternalMovieProvider implements MovieProvider {

    @Override
    public List<MovieDto> getMovies() {
        // Simulated external API response
        return Arrays.asList(
                new MovieDto(1001L, "Interstellar", "Interstellar", "Short Sci-Fi Description",
                        "Full Sci-Fi Description", "trailer123", 2014, "Sci-Fi", false, "poster_url", 8.6, 2000, null, null),
                new MovieDto(1002L, "Inception", "Inception", "Short Thriller Description",
                        "Full Thriller Description", "trailer456", 2010, "Thriller", false, "poster_url", 8.8, 2500, null, null)
        );
    }
}
