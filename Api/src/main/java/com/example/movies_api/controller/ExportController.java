package com.example.movies_api.controller;

import com.example.movies_api.service.ExportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/exports")
@RequiredArgsConstructor
public class ExportController {
    
    private final ExportService exportService;
    
    /**
     * Export movies in the specified format
     * @param format The format to export (csv, json, xml)
     * @param source The data source (local, external)
     * @return Response with the result of the export operation
     */
    @GetMapping
    public ResponseEntity<String> exportMovies(
            @RequestParam(defaultValue = "csv") String format,
            @RequestParam(defaultValue = "local") String source) {
        
        String result = exportService.exportMovies(format, source);
        return ResponseEntity.ok(result);
    }
}
