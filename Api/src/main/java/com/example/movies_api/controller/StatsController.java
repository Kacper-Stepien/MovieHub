package com.example.movies_api.controller;

import com.example.movies_api.stats.StatsCollector;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/stats")
public class StatsController {

    @GetMapping("/summary")
    public ResponseEntity<Map<String, Object>> getStatsSummary() {
        StatsCollector stats = StatsCollector.getInstance();
        
        Map<String, Object> summary = new HashMap<>();
        summary.put("totalCalls", stats.getCallCount());
        summary.put("successfulCalls", stats.getTotalSuccessCalls());
        summary.put("failedCalls", stats.getTotalFailedCalls());
        summary.put("lastCallTime", stats.getLastCallTime());
        
        return ResponseEntity.ok(summary);
    }
    
    @GetMapping("/detailed")
    public ResponseEntity<Map<String, Object>> getDetailedStats() {
        StatsCollector stats = StatsCollector.getInstance();
        
        Map<String, Object> details = new HashMap<>();
        details.put("totalCalls", stats.getCallCount());
        details.put("methodStats", stats.getAllMethodStats());
        details.put("successStats", stats.getSuccessStats());
        details.put("failedStats", stats.getFailedStats());
        
        return ResponseEntity.ok(details);
    }
    
    @GetMapping("/report")
    public ResponseEntity<String> getStatsReport() {
        String report = StatsCollector.getInstance().getStatsReport();
        return ResponseEntity.ok(report);
    }
}

// Wykorzystanie Singleton Eager Initialization
// ////////////////////////////////////////////////////////////////////////
// @RestController
// @RequestMapping("/stats")
// public class StatsController {

//     @GetMapping
//     public ResponseEntity<StatsDto> getStats() {
//         StatsCollector statsCollector = StatsCollector.getInstance();

//         StatsDto statsDto = new StatsDto();
//         statsDto.setCallCount(statsCollector.getCallCount());
//         statsDto.setLastCallTime(statsCollector.getLastCallTime());

//         return ResponseEntity.ok(statsDto);
//     }
// }