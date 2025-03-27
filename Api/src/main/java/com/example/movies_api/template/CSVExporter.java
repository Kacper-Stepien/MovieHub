package com.example.movies_api.template;

import com.example.movies_api.dto.MovieDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Concrete implementation of DataExportTemplate for CSV format.
 */
@Component
public class CSVExporter extends DataExportTemplate<MovieDto> {

    @Override
    protected String formatData(List<MovieDto> data) {
        StringBuilder sb = new StringBuilder();
        
        // Add header row
        sb.append("Id,Title,OriginalTitle,ReleaseYear,Genre,Rating\n");
        
        // Add data rows
        for (MovieDto movie : data) {
            sb.append(movie.getId()).append(",")
              .append(escapeCSV(movie.getTitle())).append(",")
              .append(escapeCSV(movie.getOriginalTitle())).append(",")
              .append(movie.getReleaseYear()).append(",")
              .append(escapeCSV(movie.getGenre())).append(",")
              .append(movie.getAvgRating()).append("\n");
        }
        
        return sb.toString();
    }
    
    @Override
    protected String postProcess(String fileName) {
        return "CSV export completed successfully to " + fileName;
    }
    
    /**
     * Helper method to escape CSV special characters
     */
    private String escapeCSV(String value) {
        if (value == null) return "";
        // Escape quotes and wrap in quotes if contains comma
        if (value.contains(",") || value.contains("\"") || value.contains("\n")) {
            return "\"" + value.replace("\"", "\"\"") + "\"";
        }
        return value;
    }
}
