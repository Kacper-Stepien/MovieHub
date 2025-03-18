package com.example.movies_api.collection;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CollectionItemDto {
    private Long id;
    private String name;
    private String type;    // "GROUP" czy "MOVIE"
    private Long movieId;   // null jeśli to group

    public static CollectionItemDto fromEntity(CollectionItem item) {
        CollectionItemDto dto = new CollectionItemDto();
        dto.setId(item.getId());
        dto.setName(item.getName());

        if (item instanceof CollectionGroup) {
            dto.setType("GROUP");
        } else if (item instanceof MovieItem mi) {
            dto.setType("MOVIE");
            if (mi.getMovie() != null) {
                dto.setMovieId(mi.getMovie().getId());
            }
        }
        return dto;
    }
}
