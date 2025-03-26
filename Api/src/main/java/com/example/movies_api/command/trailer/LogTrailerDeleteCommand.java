package com.example.movies_api.command.trailer;

import com.example.movies_api.command.LogCommand;
import com.example.movies_api.logger.CommandLogger;
import com.example.movies_api.logger.FileLogWriter;

public class LogTrailerDeleteCommand implements LogCommand {
    private final Long trailerId;

    public LogTrailerDeleteCommand(Long trailerId) {
        this.trailerId = trailerId;
    }

    @Override
    public void execute(FileLogWriter writer) {
        writer.writeToLog("trailers.log", "Usunięto trailer o ID: " + trailerId);
    }
}
