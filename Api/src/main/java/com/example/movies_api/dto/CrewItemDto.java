package com.example.movies_api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CrewItemDto {
    private Long id;
    private String name;
    private String type; // np. "GROUP" lub "MEMBER"
}