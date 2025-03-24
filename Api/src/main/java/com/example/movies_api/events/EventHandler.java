package com.example.movies_api.events;

@FunctionalInterface
public interface EventHandler {
    void update(EventType eventType, Object sender);
}
