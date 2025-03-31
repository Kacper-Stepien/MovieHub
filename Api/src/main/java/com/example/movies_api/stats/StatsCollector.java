package com.example.movies_api.stats;

// import com.example.movies_api.events.EventType;
// import com.example.movies_api.events.Mediator;
// import java.util.Objects;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

// Singleton Eager Initialization //////////////////////////////////////////////////////////////////////////////////////
public class StatsCollector {
    private static final StatsCollector INSTANCE = new StatsCollector();

    private int callCount;
    private LocalDateTime lastCallTime;
    private Map<String, Integer> methodCallStats;
    private Map<String, Integer> successCallStats;
    private Map<String, Integer> failedCallStats;
    private Map<String, LocalDateTime> lastMethodCallTime;

    private StatsCollector() {
        this.callCount = 0;
        this.lastCallTime = null;
        this.methodCallStats = new ConcurrentHashMap<>();
        this.successCallStats = new ConcurrentHashMap<>();
        this.failedCallStats = new ConcurrentHashMap<>();
        this.lastMethodCallTime = new ConcurrentHashMap<>();
    }

    public static StatsCollector getInstance() {
        return INSTANCE;
    }

    public synchronized void recordCall() {
        callCount++;
        lastCallTime = LocalDateTime.now();
    }
    
    public synchronized void recordMethodCall(String methodName) {
        int count = methodCallStats.getOrDefault(methodName, 0);
        methodCallStats.put(methodName, count + 1);
        lastMethodCallTime.put(methodName, LocalDateTime.now());
        recordCall();
    }
    
    public synchronized void recordSuccessfulCall(String methodName) {
        int count = successCallStats.getOrDefault(methodName, 0);
        successCallStats.put(methodName, count + 1);
    }
    
    public synchronized void recordFailedCall(String methodName) {
        int count = failedCallStats.getOrDefault(methodName, 0);
        failedCallStats.put(methodName, count + 1);
    }
    
    public int getMethodCallCount(String methodName) {
        return methodCallStats.getOrDefault(methodName, 0);
    }

    public int getCallCount() {
        return callCount;
    }

    public LocalDateTime getLastCallTime() {
        return lastCallTime;
    }
    
    public LocalDateTime getLastMethodCallTime(String methodName) {
        return lastMethodCallTime.get(methodName);
    }
    
    public Map<String, Integer> getAllMethodStats() {
        return new HashMap<>(methodCallStats);
    }
    
    public Map<String, Integer> getSuccessStats() {
        return new HashMap<>(successCallStats);
    }
    
    public Map<String, Integer> getFailedStats() {
        return new HashMap<>(failedCallStats);
    }
    
    public int getTotalSuccessCalls() {
        return successCallStats.values().stream().mapToInt(Integer::intValue).sum();
    }
    
    public int getTotalFailedCalls() {
        return failedCallStats.values().stream().mapToInt(Integer::intValue).sum();
    }
    
    // Responsibility of report generation moved to StatsReportGenerator
    public String getStatsReport() {
        return StatsReportGenerator.generateReport(this);
    }
    
    // Wyświetla statystyki w czytelnym formacie
    // public String getStatsReport() {
    //     StringBuilder report = new StringBuilder();
    //     report.append("=== Stats Collector Report ===\n");
    //     report.append("Total calls: ").append(callCount).append("\n");
    //     report.append("Successful calls: ").append(getTotalSuccessCalls()).append("\n");
    //     report.append("Failed calls: ").append(getTotalFailedCalls()).append("\n");
    //     report.append("Last call time: ").append(lastCallTime).append("\n\n");
        
    //     report.append("=== Endpoint Stats ===\n");
    //     methodCallStats.forEach((endpoint, count) -> {
    //         int success = successCallStats.getOrDefault(endpoint, 0);
    //         int failed = failedCallStats.getOrDefault(endpoint, 0);
    //         LocalDateTime lastCall = lastMethodCallTime.get(endpoint);
            
    //         report.append(endpoint).append(":\n");
    //         report.append("  Calls: ").append(count).append("\n");
    //         report.append("  Success: ").append(success).append("\n");
    //         report.append("  Failed: ").append(failed).append("\n");
    //         report.append("  Last call: ").append(lastCall).append("\n\n");
    //     });
        
    //     return report.toString();
    // }

}

// Mediator 1
// //////////////////////////////////////////////////////////////////////////////////////////////////////////
// public class StatsCollector implements Mediator {
// private static final StatsCollector INSTANCE = new StatsCollector();
//
// private final Map<EventType, Integer> eventCounts = new HashMap<>();
// private final Map<EventType, LocalDateTime> lastEventTime = new HashMap<>();
//
// private StatsCollector() {
// for (EventType eventType : EventType.values()) {
// eventCounts.put(eventType, 0);
// lastEventTime.put(eventType, null);
// }
// }
//
// public static StatsCollector getInstance() {
// return INSTANCE;
// }
//
// @Override
// public synchronized void notify(Object sender, EventType eventType) {
// int newCount = eventCounts.getOrDefault(eventType, 0) + 1;
// eventCounts.put(eventType, newCount);
// lastEventTime.put(eventType, LocalDateTime.now());
// System.out.println("StatsCollector: Zdarzenie " + eventType + " odnotowane.
// Liczba wywołań: " + newCount);
// }
//
// public int getEventCount(EventType eventType) {
// return eventCounts.getOrDefault(eventType, 0);
// }
//
// public LocalDateTime getLastEventTime(EventType eventType) {
// return lastEventTime.get(eventType);
// }
//
// public int getCallCount() {
// return eventCounts.values().stream().mapToInt(Integer::intValue).sum();
// }
//
// public LocalDateTime getLastCallTime() {
// return lastEventTime.values().stream()
// .filter(Objects::nonNull)
// .max(LocalDateTime::compareTo)
// .orElse(null);
// }
// }