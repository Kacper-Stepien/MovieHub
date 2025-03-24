package com.example.movies_api.trailer_data_provider;

import com.example.movies_api.dto.TrailerDto;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
@Qualifier("externalTrailerProvider")
public class ExternalTrailerProvider implements TrailerProvider {

    @Override
    public List<TrailerDto> getTrailers() {
        return Arrays.asList(
                new TrailerDto(1001L, "Interstellar Trailer", "trailer123", "A Sci-Fi journey through space", "https://image-url/interstellar.jpg"),
                new TrailerDto(1002L, "Inception Trailer", "trailer456", "A mind-bending thriller", "https://image-url/inception.jpg")
        );
    }
}