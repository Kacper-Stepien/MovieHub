package com.example.movies_api.facade.trailer_facade;

import com.example.movies_api.dto.TrailerDto;
import com.example.movies_api.trailer_data_provider.LocalTrailerProvider;
import com.example.movies_api.trailer_data_provider.ExternalTrailerProvider;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
//2/3 trailer facade that hides fetching the data from different sources - if the data is not found in the local database it is fetched from external source
//[previously] - that file did not exist and was implemented from scratch
public class TrailerFacadeImpl implements TrailerFacade {

    private final LocalTrailerProvider localProvider;
    private final ExternalTrailerProvider externalProvider;

    public TrailerFacadeImpl(LocalTrailerProvider localProvider, ExternalTrailerProvider externalProvider) {
        this.localProvider = localProvider;
        this.externalProvider = externalProvider;
    }

    @Override
    public List<TrailerDto> getAllTrailers() {
        List<TrailerDto> localTrailers = localProvider.getTrailers();
        List<TrailerDto> externalTrailers = externalProvider.getTrailers();

        // Porównujemy po tytule (title)
        Set<String> localTitles = localTrailers.stream()
                .map(TrailerDto::getTitle)
                .map(String::toLowerCase) // case-insensitive
                .collect(Collectors.toSet());

        List<TrailerDto> uniqueExternal = externalTrailers.stream()
                .filter(trailer -> !localTitles.contains(trailer.getTitle().toLowerCase()))
                .toList();

        List<TrailerDto> all = new ArrayList<>(localTrailers);
        all.addAll(uniqueExternal);
        return all;
    }
}
