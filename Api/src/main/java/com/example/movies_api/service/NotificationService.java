package com.example.movies_api.service;

import com.example.movies_api.strategy.notification.EmailNotificationStrategy;
import com.example.movies_api.strategy.notification.NotificationStrategy;
import com.example.movies_api.strategy.notification.SmsNotificationStrategy;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {
    private final EmailNotificationStrategy emailStrategy;
    private final SmsNotificationStrategy smsStrategy;
    private NotificationStrategy currentStrategy;

    public NotificationService(EmailNotificationStrategy emailStrategy,
                              SmsNotificationStrategy smsStrategy) {
        this.emailStrategy = emailStrategy;
        this.smsStrategy = smsStrategy;
        // Default to email strategy
        this.currentStrategy = emailStrategy;
    }

    public void setNotificationStrategy(String type) {
        if ("sms".equalsIgnoreCase(type)) {
            this.currentStrategy = smsStrategy;
        } else {
            this.currentStrategy = emailStrategy;
        }
    }

    public void sendNotification(String recipient, String subject, String message) {
        currentStrategy.notify(recipient, subject, message);
    }
}
