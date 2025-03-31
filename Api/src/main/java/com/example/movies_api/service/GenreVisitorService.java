package com.example.movies_api.service;

import com.example.movies_api.model.Genre;
import com.example.movies_api.repository.GenreRepository;
import com.example.movies_api.repository.MovieRepository;
import com.example.movies_api.visitor.GenreReportFormatter;
import com.example.movies_api.visitor.MovieCountGenreVisitor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Service class responsible for handling visitor pattern operations on genres.
 * Following the Single Responsibility Principle, this service focuses only on 
 * visitor-related functionality for genres.
 */
@Service
@RequiredArgsConstructor
public class GenreVisitorService {

    private final MovieRepository movieRepository;
    private final GenreRepository genreRepository;
    private final GenreReportFormatter reportFormatter;

    /**
     * Generates a report of movie counts by genre starting from a root genre
     * 
     * @param rootGenreId The ID of the root genre to start from
     * @return A formatted report showing movie counts by genre
     */
    public String generateMovieCountReport(Long rootGenreId) {
        Genre root = genreRepository.findById(rootGenreId)
                .orElseThrow(() -> new RuntimeException("Gatunek nie znaleziony: " + rootGenreId));
        
        MovieCountGenreVisitor visitor = new MovieCountGenreVisitor(movieRepository);
        root.accept(visitor);
        
        return reportFormatter.formatReport(visitor.getMovieCounts());
    }
}
