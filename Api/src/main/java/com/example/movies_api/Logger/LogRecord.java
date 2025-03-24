package com.example.movies_api.Logger;

import com.example.movies_api.stats.EventType;

import java.time.LocalDateTime;

public class LogRecord {
    private final EventType eventType;
    private final LocalDateTime timestamp;
    private final String sender;

    public LogRecord(EventType eventType, LocalDateTime timestamp, Object sender) {
        this.eventType = eventType;
        this.timestamp = timestamp;
        // Aby łatwiej identyfikować nadawcę, pobieramy nazwę klasy
        this.sender = sender.getClass().getSimpleName();
    }

    @Override
    public String toString() {
        return "[" + timestamp + "] Event: " + eventType + " from " + sender;
    }
}