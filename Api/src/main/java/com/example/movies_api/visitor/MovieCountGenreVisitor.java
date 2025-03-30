package com.example.movies_api.visitor;

import com.example.movies_api.model.Genre;
import com.example.movies_api.repository.MovieRepository;

import java.util.HashMap;
import java.util.Map;

// Visitor 3 ///////////////////////////////////////////////////////////////////////////////////////////////////////////
public class MovieCountGenreVisitor implements GenreVisitor {
    private final MovieRepository movieRepository;
    private final Map<Genre, Long> movieCounts = new HashMap<>();

    public MovieCountGenreVisitor(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @Override
    public void visit(Genre genre, int depth) {
        long count = movieRepository.countByGenre_Id(genre.getId());
        movieCounts.put(genre, count);
        String indent = "  ".repeat(depth);
        System.out.println(indent + "Genre: " + genre.getName() + " -> " + count + " movies");
    }

    public Map<Genre, Long> getMovieCounts() {
        return movieCounts;
    }

    public String getReport() {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<Genre, Long> entry : movieCounts.entrySet()) {
            sb.append("Genre: ").append(entry.getKey().getName())
                    .append(" -> ").append(entry.getValue()).append(" movies\n");
        }
        return sb.toString();
    }
}