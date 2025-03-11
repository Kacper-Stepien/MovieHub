package com.example.movies_api.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrailerDto {
    private Long id;
    private String title;
    private String youtubeTrailerId;
    private String description;
    private String thumbnail;

}
