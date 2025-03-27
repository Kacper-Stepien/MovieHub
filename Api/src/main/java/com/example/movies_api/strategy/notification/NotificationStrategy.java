package com.example.movies_api.strategy.notification;

/**
 * Strategy interface for different notification methods
 */
public interface NotificationStrategy {
    void notify(String recipient, String subject, String message);
}
