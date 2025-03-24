package com.example.movies_api.events;

import java.util.ArrayList;
import java.util.List;

public class EventMediator implements Mediator {
    private final List<EventHandler> handlers = new ArrayList<>();

    public void subscribe(EventHandler handler) {
        handlers.add(handler);
    }

    public void unsubscribe(EventHandler handler) {
        handlers.remove(handler);
    }

    @Override
    public synchronized void notify(Object sender, EventType eventType) {
        for (EventHandler handler : handlers) {
            handler.update(eventType, sender);
        }
    }
}
