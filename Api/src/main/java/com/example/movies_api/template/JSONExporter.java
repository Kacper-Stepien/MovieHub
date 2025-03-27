package com.example.movies_api.template;

import com.example.movies_api.dto.MovieDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Concrete implementation of DataExportTemplate for JSON format.
 */
@Component
public class JSONExporter extends DataExportTemplate<MovieDto> {

    private final ObjectMapper objectMapper;
    
    public JSONExporter() {
        this.objectMapper = new ObjectMapper();
    }
    
    @Override
    protected String formatData(List<MovieDto> data) {
        try {
            return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(data);
        } catch (Exception e) {
            throw new RuntimeException("Error formatting data to JSON", e);
        }
    }
    
    @Override
    protected void validateData(List<MovieDto> data) {
        super.validateData(data);
        // Additional validations specific to JSON export
        for (MovieDto movie : data) {
            if (movie.getTitle() == null || movie.getTitle().isEmpty()) {
                throw new IllegalArgumentException("Movie title cannot be null or empty for JSON export");
            }
        }
    }
    
    @Override
    protected String postProcess(String fileName) {
        return "JSON data successfully exported to " + fileName + " with " + 
               "pretty printing enabled.";
    }
}
