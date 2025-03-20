package com.example.movies_api.trailer_data_provider;

import com.example.movies_api.dto.TrailerDto;

import java.util.List;

public interface TrailerProvider {
    List<TrailerDto> getTrailers();
}