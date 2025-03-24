package com.example.movies_api.service;

import com.example.movies_api.dto.MovieDto;
import com.example.movies_api.dto.TrailerDto;
import com.example.movies_api.exception.BadRequestException;
import com.example.movies_api.factory.VideoFactory;
import com.example.movies_api.mapper.TrailerDtoMapper;
import com.example.movies_api.model.Trailer;
import com.example.movies_api.proxy.TrailerDataProxy;
import com.example.movies_api.repository.TrailerRepository;
import com.example.movies_api.trailer_data_provider.ExternalTrailerProvider;
import com.example.movies_api.trailer_data_provider.LocalTrailerProvider;
import com.example.movies_api.trailer_data_provider.TrailerProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TrailerService {

    private final TrailerRepository trailerRepository;

    private final LocalTrailerProvider localTrailerProvider;
    private final ExternalTrailerProvider externalTrailerProvider;
    private final TrailerDataProxy trailerDataProxy;

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

    //depricated -- after adding trailer bridge
    public List<TrailerDto> findAllTrailers() {
        return trailerRepository.findAll().stream()
                .map(TrailerDtoMapper::map)
                .collect(Collectors.toList());
    }


    public List<TrailerDto> getTrailers(String source) {
        // Using the proxy to get trailers
        if (source.equalsIgnoreCase("external")) {
            return trailerDataProxy.getTrailersFromSource("external");
        }
        return trailerDataProxy.getTrailersFromSource("local");
    }
}
