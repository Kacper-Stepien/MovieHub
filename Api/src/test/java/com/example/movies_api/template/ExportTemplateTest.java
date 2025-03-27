package com.example.movies_api.template;

import com.example.movies_api.dto.MovieDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ExportTemplateTest {
    
    private CSVExporter csvExporter;
    private JSONExporter jsonExporter;
    private XMLExporter xmlExporter;
    private List<MovieDto> testMovies;
    
    @TempDir
    Path tempDir;
    
    @BeforeEach
    void setUp() {
        csvExporter = new CSVExporter();
        jsonExporter = new JSONExporter();
        xmlExporter = new XMLExporter();
        
        // Create test data
        MovieDto movie1 = new MovieDto();
        movie1.setId(1L);
        movie1.setTitle("Test Movie 1");
        movie1.setOriginalTitle("Original Test 1");
        movie1.setReleaseYear(2020);
        movie1.setGenre("Action");
        movie1.setAvgRating(8.5);
        
        MovieDto movie2 = new MovieDto();
        movie2.setId(2L);
        movie2.setTitle("Test Movie 2");
        movie2.setOriginalTitle("Original Test 2");
        movie2.setReleaseYear(2021);
        movie2.setGenre("Comedy");
        movie2.setAvgRating(7.8);
        
        testMovies = Arrays.asList(movie1, movie2);
    }
    
    @Test
    void testCSVExport() throws IOException {
        String fileName = tempDir.resolve("test.csv").toString();
        String result = csvExporter.exportData(testMovies, fileName);
        
        assertTrue(result.contains("CSV export completed successfully"));
        assertTrue(Files.exists(Path.of(fileName)));
        
        String content = Files.readString(Path.of(fileName));
        assertTrue(content.contains("Id,Title,OriginalTitle,ReleaseYear,Genre,Rating"));
        assertTrue(content.contains("1,Test Movie 1,Original Test 1,2020,Action,8.5"));
    }
    
    @Test
    void testJSONExport() throws IOException {
        String fileName = tempDir.resolve("test.json").toString();
        String result = jsonExporter.exportData(testMovies, fileName);
        
        assertTrue(result.contains("JSON data successfully exported"));
        assertTrue(Files.exists(Path.of(fileName)));
        
        String content = Files.readString(Path.of(fileName));
        assertTrue(content.contains("\"id\" : 1"));
        assertTrue(content.contains("\"title\" : \"Test Movie 1\""));
    }
    
    @Test
    void testXMLExport() throws IOException {
        String fileName = tempDir.resolve("test.xml").toString();
        String result = xmlExporter.exportData(testMovies, fileName);
        
        assertTrue(result.contains("XML export completed successfully"));
        assertTrue(Files.exists(Path.of(fileName)));
        
        String content = Files.readString(Path.of(fileName));
        assertTrue(content.contains("<movies>"));
        assertTrue(content.contains("<id>1</id>"));
        assertTrue(content.contains("<title>Test Movie 1</title>"));
    }
    
    @Test
    void testValidateEmptyData() {
        // Test that all exporters throw exception for empty data
        List<MovieDto> emptyList = Collections.emptyList();
        
        assertThrows(IllegalArgumentException.class, () -> csvExporter.exportData(emptyList, "test.csv"));
        assertThrows(IllegalArgumentException.class, () -> jsonExporter.exportData(emptyList, "test.json"));
        assertThrows(IllegalArgumentException.class, () -> xmlExporter.exportData(emptyList, "test.xml"));
    }
    
    @Test
    void testJSONValidation() {
        // Test JSON-specific validation (title cannot be empty)
        MovieDto invalidMovie = new MovieDto();
        invalidMovie.setId(3L);
        invalidMovie.setTitle(""); // Empty title should trigger validation error
        
        List<MovieDto> invalidMovies = Collections.singletonList(invalidMovie);
        
        assertThrows(IllegalArgumentException.class, 
            () -> jsonExporter.exportData(invalidMovies, "invalid.json"));
    }
}
