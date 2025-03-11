package com.example.movies_api.service;

import com.example.movies_api.dto.TrailerDto;
import com.example.movies_api.exception.BadRequestException;
import com.example.movies_api.factory.VideoFactory;
import com.example.movies_api.model.Trailer;
import com.example.movies_api.repository.TrailerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TrailerService {

    private final TrailerRepository trailerRepository;

    public TrailerDto addTrailer(TrailerDto trailerDto) {
        if (trailerDto.getYoutubeTrailerId() == null || trailerDto.getYoutubeTrailerId().isEmpty()) {
            throw new BadRequestException("YouTube ID cannot be empty");
        }

        Trailer trailer = VideoFactory.createTrailer(
                trailerDto.getTitle(),
                trailerDto.getYoutubeTrailerId(),
                trailerDto.getDescription(),
                trailerDto.getThumbnail()
        );

        trailer = trailerRepository.save(trailer);

        return new TrailerDto(trailer.getId(),
                trailer.getTitle(),
                trailer.getYoutubeTrailerId(),
                trailer.getDescription(),
                trailer.getThumbnail());
    }
}
