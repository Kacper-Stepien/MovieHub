package com.example.movies_api.events;

import com.example.movies_api.model.LogEntry;
import com.example.movies_api.service.LogEntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;

// Mediator 3 //////////////////////////////////////////////////////////////////////////////////////////////////////////
@Component
public class PersistenceEventHandler implements EventHandler {

    private final LogEntryService logEntryService;

    @Autowired
    public PersistenceEventHandler(LogEntryService logEntryService) {
        this.logEntryService = logEntryService;
    }

    @Override
    public void update(EventType eventType, Object sender) {
        String senderName = sender.getClass().getSimpleName();
        String message = "Zdarzenie " + eventType + " od " + senderName + " zostało zapisane do bazy.";
        LogEntry entry = new LogEntry(null, eventType.name(), senderName, LocalDateTime.now(), message);
        logEntryService.saveLog(entry);
        System.out.println("PersistenceEventHandler: " + message);
    }
}