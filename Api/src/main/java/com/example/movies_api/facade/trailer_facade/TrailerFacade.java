package com.example.movies_api.facade.trailer_facade;

import com.example.movies_api.dto.TrailerDto;
import java.util.List;



//2/3 trailer facade that hides fetching the data from different sources - if the data is not found in the local database it is fetched from external source
//[previously] - that file did not exist and was implemented from scratch
public interface TrailerFacade {
    List<TrailerDto> getAllTrailers();
}
