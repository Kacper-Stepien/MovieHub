package com.example.movies_api.stats;

import com.example.movies_api.events.EventType;
import com.example.movies_api.events.Mediator;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

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

// Mediator 1 //////////////////////////////////////////////////////////////////////////////////////////////////////////
//public class StatsCollector implements Mediator {
//    private static final StatsCollector INSTANCE = new StatsCollector();
//
//    private final Map<EventType, Integer> eventCounts = new HashMap<>();
//    private final Map<EventType, LocalDateTime> lastEventTime = new HashMap<>();
//
//    private StatsCollector() {
//        for (EventType eventType : EventType.values()) {
//            eventCounts.put(eventType, 0);
//            lastEventTime.put(eventType, null);
//        }
//    }
//
//    public static StatsCollector getInstance() {
//        return INSTANCE;
//    }
//
//    @Override
//    public synchronized void notify(Object sender, EventType eventType) {
//        int newCount = eventCounts.getOrDefault(eventType, 0) + 1;
//        eventCounts.put(eventType, newCount);
//        lastEventTime.put(eventType, LocalDateTime.now());
//        System.out.println("StatsCollector: Zdarzenie " + eventType + " odnotowane. Liczba wywołań: " + newCount);
//    }
//
//    public int getEventCount(EventType eventType) {
//        return eventCounts.getOrDefault(eventType, 0);
//    }
//
//    public LocalDateTime getLastEventTime(EventType eventType) {
//        return lastEventTime.get(eventType);
//    }
//
//    public int getCallCount() {
//        return eventCounts.values().stream().mapToInt(Integer::intValue).sum();
//    }
//
//    public LocalDateTime getLastCallTime() {
//        return lastEventTime.values().stream()
//                .filter(Objects::nonNull)
//                .max(LocalDateTime::compareTo)
//                .orElse(null);
//    }
//}
