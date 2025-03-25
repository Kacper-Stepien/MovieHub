package com.example.movies_api.facade.trailer_facade;

import com.example.movies_api.dto.TrailerDto;
import java.util.List;

public interface TrailerFacade {
    List<TrailerDto> getAllTrailers();
}
