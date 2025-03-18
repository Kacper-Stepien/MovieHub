package com.example.movies_api.collection;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CollectionItemTreeDto {
    private Long id;
    private String name;
    private String type;    // "MOVIE" lub "GROUP"
    private Long movieId;   // null jeśli to GROUP
    private List<CollectionItemTreeDto> children = new ArrayList<>();
}