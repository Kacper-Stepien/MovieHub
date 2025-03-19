package com.example.movies_api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


// Kompozyt 3 //////////////////////////////////////////////////////////////////////////////////////////////////////
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GenreTreeDto {
    private Long id;
    private String name;
    private String description;
    private List<GenreTreeDto> children = new ArrayList<>();
}