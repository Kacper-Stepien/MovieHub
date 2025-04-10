package com.example.movies_api.controller.trailer_adapter;

import com.example.movies_api.dto.TrailerDto;
import java.util.List;

public interface TrailerAdapter {
    String addTrailer(TrailerDto trailerData) throws Exception;
    List<TrailerDto> getAllTrailers() throws Exception;
    //Open-Close Principle 2/3 (data steering) [added lines]
    String getSupportedContentType(); // NOWE
}