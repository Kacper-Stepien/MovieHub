package com.example.movies_api.collection;

import com.example.movies_api.model.Movie;
import com.example.movies_api.model.User;
import jakarta.persistence.*;

@Entity
@DiscriminatorValue("MOVIE_ITEM")
public class MovieItem extends CollectionItem {

    @ManyToOne
    @JoinColumn(name = "movie_ref_id")
    private Movie movie;

    public MovieItem() {
    }

    public MovieItem(String name, User owner, Movie movie) {
        super(name, owner);
        this.movie = movie;
    }

    public Movie getMovie() {
        return movie;
    }

    @Override
    public void addItem(CollectionItem item) {
        throw new UnsupportedOperationException("MovieItem jest liściem i nie może mieć dzieci");
    }

    @Override
    public void removeItem(CollectionItem item) {
        throw new UnsupportedOperationException("MovieItem jest liściem i nie może mieć dzieci");
    }

    @Override
    public String show(String indent) {
        String movieTitle = (movie != null) ? movie.getTitle() : "(brak powiązanego filmu)";
        return indent + "- " + getName() + " [film=" + movieTitle + "]";
    }

    @Override
    public int countMovies() {
        return 1;
    }
}