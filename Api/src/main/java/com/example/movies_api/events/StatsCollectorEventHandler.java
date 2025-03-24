package com.example.movies_api.events;

import com.example.movies_api.stats.StatsCollector;

// Mediator 1 //////////////////////////////////////////////////////////////////////////////////////////////////////////
public class StatsCollectorEventHandler implements EventHandler {
    @Override
    public void update(EventType eventType, Object sender) {
        StatsCollector.getInstance().recordCall();
        System.out.println("StatsCollectorEventHandler: Zarejestrowano zdarzenie " + eventType);
    }
}
