package com.example.movies_api.trailer_data_provider;

import com.example.movies_api.dto.TrailerDto;
import com.example.movies_api.mapper.TrailerDtoMapper;
import com.example.movies_api.repository.TrailerRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Qualifier("localTrailerProvider")
public class LocalTrailerProvider implements TrailerProvider {
    private final TrailerRepository trailerRepository;

    public LocalTrailerProvider(TrailerRepository trailerRepository) {
        this.trailerRepository = trailerRepository;
    }

    @Override
    public List<TrailerDto> getTrailers() {
        return trailerRepository.findAll().stream()
                .map(TrailerDtoMapper::map)
                .toList();
    }
}