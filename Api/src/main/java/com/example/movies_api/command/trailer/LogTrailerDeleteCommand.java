package com.example.movies_api.command.trailer;

import com.example.movies_api.command.LogCommand;
import com.example.movies_api.logger.CommandLogger;
import com.example.movies_api.logger.FileLogWriter;

//1/3 command pattern - the command is issued on the trailer deletion
//[previously] that file did not exist
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
