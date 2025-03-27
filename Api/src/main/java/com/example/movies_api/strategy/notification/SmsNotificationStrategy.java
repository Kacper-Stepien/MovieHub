package com.example.movies_api.strategy.notification;

import org.springframework.stereotype.Component;

@Component
public class SmsNotificationStrategy implements NotificationStrategy {
    @Override
    public void notify(String recipient, String subject, String message) {
        // In a real implementation, this would use SMS gateway API
        System.out.println("Sending SMS notification to: " + recipient);
        System.out.println("Message: " + subject + " - " + message);
    }
}
