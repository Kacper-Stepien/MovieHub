package com.example.movies_api.command.trailer;

import com.example.movies_api.command.LogCommand;
import com.example.movies_api.dto.TrailerDto;
import com.example.movies_api.logger.CommandLogger;

public class LogTrailerUpdateCommand implements LogCommand {
    private final TrailerDto trailer;

    public LogTrailerUpdateCommand(TrailerDto trailer) {
        this.trailer = trailer;
    }

    @Override
    public void execute() {
        CommandLogger.log("trailers.log","Zaktualizowano trailer: " + trailer.getTitle());
    }
}
