package com.example.movies_api.command.trailer;

import com.example.movies_api.command.LogCommand;
import com.example.movies_api.logger.CommandLogger;

public class LogTrailerDeleteCommand implements LogCommand {
    private final Long trailerId;

    public LogTrailerDeleteCommand(Long trailerId) {
        this.trailerId = trailerId;
    }

    @Override
    public void execute() {
        CommandLogger.log("trailers.log","Usunięto trailer o ID: " + trailerId);
    }
}
