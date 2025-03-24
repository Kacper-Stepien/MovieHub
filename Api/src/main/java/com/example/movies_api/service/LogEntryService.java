package com.example.movies_api.service;

import com.example.movies_api.model.LogEntry;
import com.example.movies_api.repository.LogEntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LogEntryService {
    private final LogEntryRepository logEntryRepository;

    @Autowired
    public LogEntryService(LogEntryRepository logEntryRepository) {
        this.logEntryRepository = logEntryRepository;
    }

    public void saveLog(LogEntry entry) {
        logEntryRepository.save(entry);
    }
}
