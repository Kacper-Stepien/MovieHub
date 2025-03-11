package com.example.movies_api.mapper;

import com.example.movies_api.dto.TrailerDto;
import com.example.movies_api.model.Trailer;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class TrailerDtoMapper {
    public static TrailerDto map(Trailer trailer) {
        TrailerDto trailerDto = new TrailerDto();
        BeanUtils.copyProperties(trailer, trailerDto);
        return trailerDto;
    }
}