package com.example.movies_api.model;

import com.example.movies_api.crew.CrewGroup;
import com.example.movies_api.factory.Video;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

/*
    // Before implementing the simple factory pattern (added: Video interface, VideoFactory, changed Movie,Trailer)

@Entity
@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String originalTitle;
    private String shortDescription;
    private String description;
    private String youtubeTrailerId;
    private Integer releaseYear;

    @ManyToOne
    @JoinColumn(name = "genre_id", referencedColumnName = "id")
    private Genre genre;

    private boolean promoted;
    private String poster;

    @OneToMany(mappedBy = "movie", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private Set<Rating> ratings = new HashSet<>();

    @OneToMany(mappedBy = "movie", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private Set<Comment> comments = new HashSet<>();
}
*/

/*
//previously: Lombok builder was used (1/3)
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Movie implements Video {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String originalTitle;
    private String shortDescription;
    private String description;
    private String youtubeTrailerId;
    private Integer releaseYear;

    @ManyToOne
    @JoinColumn(name = "genre_id", referencedColumnName = "id")
    private Genre genre;

    private boolean promoted;
    private String poster;

    @OneToMany(mappedBy = "movie", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private Set<Rating> ratings = new HashSet<>();

    @OneToMany(mappedBy = "movie", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private Set<Comment> comments = new HashSet<>();

    @Override
    public String getThumbnail() {
        return poster;
    }
}
*/

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Movie implements Video {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String originalTitle;
    private String shortDescription;
    private String description;
    private String youtubeTrailerId;
    private Integer releaseYear;

    @ManyToOne
    @JoinColumn(name = "genre_id", referencedColumnName = "id")
    private Genre genre;

    private boolean promoted;
    private String poster;

    @OneToMany(mappedBy = "movie", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private Set<Rating> ratings = new HashSet<>();

    @OneToMany(mappedBy = "movie", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private Set<Comment> comments = new HashSet<>();

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "crew_root_id")
    private CrewGroup crewRoot;

    @Override
    public String getThumbnail() {
        return poster;
    }

    // Ręczna implementacja wzorca Builder
    public static MovieBuilder builder() {
        return new MovieBuilder();
    }

    public static class MovieBuilder {
        private Long id;
        private String title;
        private String originalTitle;
        private String shortDescription;
        private String description;
        private String youtubeTrailerId;
        private Integer releaseYear;
        private boolean promoted;
        private String poster;
        private Set<Rating> ratings = new HashSet<>();
        private Set<Comment> comments = new HashSet<>();

        public MovieBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public MovieBuilder title(String title) {
            this.title = title;
            return this;
        }

        public MovieBuilder originalTitle(String originalTitle) {
            this.originalTitle = originalTitle;
            return this;
        }

        public MovieBuilder shortDescription(String shortDescription) {
            this.shortDescription = shortDescription;
            return this;
        }

        public MovieBuilder description(String description) {
            this.description = description;
            return this;
        }

        public MovieBuilder youtubeTrailerId(String youtubeTrailerId) {
            this.youtubeTrailerId = youtubeTrailerId;
            return this;
        }

        public MovieBuilder releaseYear(Integer releaseYear) {
            this.releaseYear = releaseYear;
            return this;
        }

        public MovieBuilder promoted(boolean promoted) {
            this.promoted = promoted;
            return this;
        }

        public MovieBuilder poster(String poster) {
            this.poster = poster;
            return this;
        }

        public MovieBuilder ratings(Set<Rating> ratings) {
            this.ratings = ratings;
            return this;
        }

        public MovieBuilder comments(Set<Comment> comments) {
            this.comments = comments;
            return this;
        }

        public Movie build() {
            return new Movie(null, title, originalTitle, shortDescription, description, youtubeTrailerId, releaseYear, null, promoted, poster, new HashSet<>(), new HashSet<>(), null);
        }
    }
}