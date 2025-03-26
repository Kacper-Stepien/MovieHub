package com.example.movies_api.command.trailer;

import com.example.movies_api.command.LogCommand;
import com.example.movies_api.dto.TrailerDto;
import com.example.movies_api.logger.CommandLogger;
import com.example.movies_api.logger.FileLogWriter;

public class LogTrailerUpdateCommand implements LogCommand {
    private final TrailerDto trailer;

    public LogTrailerUpdateCommand(TrailerDto trailer) {
        this.trailer = trailer;
    }

    @Override
    public void execute(FileLogWriter writer){
        writer.writeToLog("trailers.log", "Zaktualizowano trailer:: " + trailer.getTitle());
    }
}
