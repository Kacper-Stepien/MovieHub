package com.example.movies_api.model;

import com.example.movies_api.factory.Video;
import jakarta.persistence.*;
import lombok.*;

/*
//previously: Lombok builder was used (2/3)
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
*/

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Trailer implements Video {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;
    private String youtubeTrailerId;
    private String thumbnail;

    @Override
    public String getThumbnail() {
        return thumbnail;
    }

    // Ręczna implementacja wzorca Builder
    public static TrailerBuilder builder() {
        return new TrailerBuilder();
    }

    public static class TrailerBuilder {
        private Long id;
        private String title;
        private String description;
        private String youtubeTrailerId;
        private String thumbnail;

        public TrailerBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public TrailerBuilder title(String title) {
            this.title = title;
            return this;
        }

        public TrailerBuilder description(String description) {
            this.description = description;
            return this;
        }

        public TrailerBuilder youtubeTrailerId(String youtubeTrailerId) {
            this.youtubeTrailerId = youtubeTrailerId;
            return this;
        }

        public TrailerBuilder thumbnail(String thumbnail) {
            this.thumbnail = thumbnail;
            return this;
        }

        public Trailer build() {
            return new Trailer(id, title, description, youtubeTrailerId, thumbnail);
        }
    }
}