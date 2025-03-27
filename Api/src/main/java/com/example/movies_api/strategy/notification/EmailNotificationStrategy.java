package com.example.movies_api.strategy.notification;

import org.springframework.stereotype.Component;

@Component
public class EmailNotificationStrategy implements NotificationStrategy {
    @Override
    public void notify(String recipient, String subject, String message) {
        // In a real implementation, this would use JavaMail or similar API
        System.out.println("Sending email notification to: " + recipient);
        System.out.println("Subject: " + subject);
        System.out.println("Message: " + message);
    }
}
