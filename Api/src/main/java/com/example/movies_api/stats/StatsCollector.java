package com.example.movies_api.stats;

import java.time.LocalDateTime;

// Singleton Eager Initialization //////////////////////////////////////////////////////////////////////////////////////
public class StatsCollector {
    private static final StatsCollector INSTANCE = new StatsCollector();

    private int callCount;
    private LocalDateTime lastCallTime;

    private StatsCollector() {
        this.callCount = 0;
        this.lastCallTime = null;
    }

    public static StatsCollector getInstance() {
        return INSTANCE;
    }

    public synchronized void recordCall() {
        callCount++;
        lastCallTime = LocalDateTime.now();
    }

    public int getCallCount() {
        return callCount;
    }

    public LocalDateTime getLastCallTime() {
        return lastCallTime;
    }
}
