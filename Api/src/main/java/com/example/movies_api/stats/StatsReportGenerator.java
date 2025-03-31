package com.example.movies_api.stats;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * Class responsible for generating statistical reports.
 * Following the Single Responsibility Principle, this class focuses only on
 * formatting and creating reports from statistics data.
 */
public class StatsReportGenerator {

    /**
     * Generates a formatted report based on the statistics from the StatsCollector
     * 
     * @param stats StatsCollector containing the statistics data
     * @return Formatted report as a string
     */
    public static String generateReport(StatsCollector stats) {
        StringBuilder report = new StringBuilder();
        report.append("=== Stats Collector Report ===\n");
        report.append("Total calls: ").append(stats.getCallCount()).append("\n");
        report.append("Successful calls: ").append(stats.getTotalSuccessCalls()).append("\n");
        report.append("Failed calls: ").append(stats.getTotalFailedCalls()).append("\n");
        report.append("Last call time: ").append(stats.getLastCallTime()).append("\n\n");
        
        report.append("=== Endpoint Stats ===\n");
        Map<String, Integer> methodCallStats = stats.getAllMethodStats();
        Map<String, Integer> successCallStats = stats.getSuccessStats();
        Map<String, Integer> failedCallStats = stats.getFailedStats();
        
        methodCallStats.forEach((endpoint, count) -> {
            int success = successCallStats.getOrDefault(endpoint, 0);
            int failed = failedCallStats.getOrDefault(endpoint, 0);
            LocalDateTime lastCall = stats.getLastMethodCallTime(endpoint);
            
            report.append(endpoint).append(":\n");
            report.append("  Calls: ").append(count).append("\n");
            report.append("  Success: ").append(success).append("\n");
            report.append("  Failed: ").append(failed).append("\n");
            report.append("  Last call: ").append(lastCall).append("\n\n");
        });
        
        return report.toString();
    }
}
