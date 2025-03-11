package com.example.movies_api.controller;

import com.example.movies_api.dto.TrailerDto;
import com.example.movies_api.service.TrailerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/trailers")
@RequiredArgsConstructor
public class TrailerController {

    private final TrailerService trailerService;

    @PostMapping("/add")
    public ResponseEntity<TrailerDto> addTrailer(@RequestBody TrailerDto trailerDto) {
        return ResponseEntity.ok(trailerService.addTrailer(trailerDto));
    }

    @GetMapping("/all")
    public ResponseEntity<List<TrailerDto>> getAllTrailers() {
        return ResponseEntity.ok(trailerService.findAllTrailers());
    }
}
