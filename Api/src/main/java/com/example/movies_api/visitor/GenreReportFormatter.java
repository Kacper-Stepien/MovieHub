package com.example.movies_api.visitor;

import com.example.movies_api.model.Genre;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Class responsible for formatting genre reports.
 * This follows the Single Responsibility Principle by separating
 * report generation from data collection.
 */
@Component
public class GenreReportFormatter {
    
    /**
     * Generates a formatted report from movie counts by genre
     * 
     * @param movieCounts Map of genres and their movie counts
     * @return Formatted report as a string
     */
    public String formatReport(Map<Genre, Long> movieCounts) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<Genre, Long> entry : movieCounts.entrySet()) {
            sb.append("Genre: ").append(entry.getKey().getName())
                    .append(" -> ").append(entry.getValue()).append(" movies\n");
        }
        return sb.toString();
    }
}
