package com.example.movies_api.controller;

import com.example.movies_api.dto.StatsDto;
import com.example.movies_api.stats.StatsCollector;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// Wykorzystanie Singleton Eager Initialization ////////////////////////////////////////////////////////////////////////
@RestController
@RequestMapping("/stats")
public class StatsController {

    @GetMapping
    public ResponseEntity<StatsDto> getStats() {
        StatsCollector statsCollector = StatsCollector.getInstance();

        StatsDto statsDto = new StatsDto();
        statsDto.setCallCount(statsCollector.getCallCount());
        statsDto.setLastCallTime(statsCollector.getLastCallTime());

        return ResponseEntity.ok(statsDto);
    }
}