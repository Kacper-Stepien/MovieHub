package com.example.movies_api.open_close;

import com.example.movies_api.model.Genre;

public class SimpleGenreFormatter implements GenreFormatter {
    @Override
    public String format(Genre genre) {
        return genre.getName() + " (" + genre.getDescription() + ")";
    }
}