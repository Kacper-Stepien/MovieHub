package com.example.movies_api.model;

import com.example.movies_api.factory.Video;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Trailer implements Video {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;
    private String youtubeTrailerId;
    private String thumbnail;
}