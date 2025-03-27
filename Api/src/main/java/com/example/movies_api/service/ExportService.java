package com.example.movies_api.service;

import com.example.movies_api.dto.MovieDto;
import com.example.movies_api.exception.BadRequestException;
import com.example.movies_api.repository.MovieRepository;
import com.example.movies_api.template.CSVExporter;
import com.example.movies_api.template.JSONExporter;
import com.example.movies_api.template.XMLExporter;
import com.example.movies_api.template.DataExportTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExportService {
    
    private final MovieService movieService;
    private final CSVExporter csvExporter;
    private final JSONExporter jsonExporter;
    private final XMLExporter xmlExporter;
    
    /**
     * Exports movies in the specified format
     * @param format The format to export (csv, json, xml)
     * @param source The data source (local, external)
     * @return The path to the exported file
     */
    public String exportMovies(String format, String source) {
        List<MovieDto> movies = movieService.getMovies(source);
        
        // Generate a unique filename with timestamp
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        String exportDir = "exports";
        
        // Ensure export directory exists
        try {
            Files.createDirectories(Paths.get(exportDir));
        } catch (IOException e) {
            throw new RuntimeException("Failed to create export directory", e);
        }
        
        String fileName = exportDir + "/movies_" + timestamp + "." + format.toLowerCase();
        
        try {
            DataExportTemplate<MovieDto> exporter = getExporter(format);
            return exporter.exportData(movies, fileName);
        } catch (IOException e) {
            throw new RuntimeException("Failed to export movies: " + e.getMessage(), e);
        }
    }
    
    /**
     * Get the appropriate exporter based on the format
     */
    private DataExportTemplate<MovieDto> getExporter(String format) {
        switch (format.toLowerCase()) {
            case "csv":
                return csvExporter;
            case "json":
                return jsonExporter;
            case "xml":
                return xmlExporter;
            default:
                throw new BadRequestException("Unsupported export format: " + format + ". Supported formats are: csv, json, xml");
        }
    }
}
