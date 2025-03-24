package com.example.movies_api.Logger;

import com.example.movies_api.stats.EventType;
import com.example.movies_api.stats.Mediator;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

// Mediator 2 //////////////////////////////////////////////////////////////////////////////////////////////////////////
public class Logger implements Mediator {
    private final List<LogRecord> logs = new ArrayList<>();

    @Override
    public void notify(Object sender, EventType eventType) {
        LogRecord record = new LogRecord(eventType, LocalDateTime.now(), sender);
        logs.add(record);
        System.out.println("Logger: " + record);
    }

    public List<LogRecord> getLogs() {
        return logs;
    }
}