package com.example.movies_api.logger;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;

public class CommandLogger {
    private static final String LOG_DIRECTORY = "api_logs";

    public static void log(String logFileName, String message) {
        try {
            Files.createDirectories(Paths.get(LOG_DIRECTORY));
            String logPath = LOG_DIRECTORY + "/" + logFileName;

            try (FileWriter writer = new FileWriter(logPath, true)) {
                writer.write(LocalDateTime.now() + " - " + message + "\n");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}