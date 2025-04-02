package com.example.movies_api.logger;



//3/3 command pattern - file saving command issue
//[previously] that file did not exist
public class Logger {
    private static final Logger INSTANCE = new Logger();

    private Logger() {
    }

    public static Logger getInstance() {
        return INSTANCE;
    }

    public void log(String message) {
        System.out.println("[LOG] " + message);
    }
}
