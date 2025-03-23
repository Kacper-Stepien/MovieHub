package com.example.movies_api.model;

import com.example.movies_api.flyweight.RatingValue;
import com.example.movies_api.flyweight.RatingValueConverter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "movie_rating")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "movie_id")
    @JsonIgnore
    private Movie movie;

    @Convert(converter = RatingValueConverter.class)
    private RatingValue rating;
}
