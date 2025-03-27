package com.example.movies_api.strategy;

import com.example.movies_api.service.NotificationService;
import com.example.movies_api.strategy.notification.EmailNotificationStrategy;
import com.example.movies_api.strategy.notification.SmsNotificationStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class NotificationStrategyTest {

    private NotificationService notificationService;
    private ByteArrayOutputStream outputStream;
    private PrintStream originalOut;

    @BeforeEach
    public void setUp() {
        // Przechwycenie wyjścia standardowego do testowania
        originalOut = System.out;
        outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
        
        // Inicjalizacja serwisu powiadomień
        notificationService = new NotificationService(
                new EmailNotificationStrategy(), 
                new SmsNotificationStrategy());
    }

    @Test
    public void testEmailNotificationStrategy() {
        // Ustawienie strategii i wysłanie powiadomienia
        notificationService.setNotificationStrategy("email");
        notificationService.sendNotification(
                "test@example.com", 
                "Test Email", 
                "This is a test email message");
        
        String output = outputStream.toString();
        
        // Sprawdzenie, czy powiadomienie email zostało wysłane
        assertTrue(output.contains("Sending email notification to: test@example.com"));
        assertTrue(output.contains("Subject: Test Email"));
        assertTrue(output.contains("Message: This is a test email message"));
    }

    @Test
    public void testSmsNotificationStrategy() {
        // Ustawienie strategii i wysłanie powiadomienia
        notificationService.setNotificationStrategy("sms");
        notificationService.sendNotification(
                "+1234567890", 
                "Test SMS", 
                "This is a test SMS message");
        
        String output = outputStream.toString();
        
        // Sprawdzenie, czy powiadomienie SMS zostało wysłane
        assertTrue(output.contains("Sending SMS notification to: +1234567890"));
        assertTrue(output.contains("Message: Test SMS - This is a test SMS message"));
    }
    
    @Test
    public void testDefaultToEmailStrategy() {
        // Bez jawnego ustawiania strategii powinno domyślnie używać email
        notificationService.sendNotification(
                "default@example.com", 
                "Default Strategy Test", 
                "This should use email strategy by default");
        
        String output = outputStream.toString();
        
        assertTrue(output.contains("Sending email notification to: default@example.com"));
    }
}
