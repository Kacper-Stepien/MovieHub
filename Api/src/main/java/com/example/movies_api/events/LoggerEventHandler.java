package com.example.movies_api.events;

import com.example.movies_api.logger.Logger;

// Mediator 2 //////////////////////////////////////////////////////////////////////////////////////////////////////////
public class LoggerEventHandler implements EventHandler {
    @Override
    public void update(EventType eventType, Object sender) {
        Logger.getInstance().log("Otrzymano zdarzenie " + eventType + " od " + sender.getClass().getSimpleName());
    }
}
