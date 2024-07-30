/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Sridhar
 */
// Product
interface Notification {
    void send(String message);
}

class EmailNotification implements Notification {
    @Override
    public void send(String message) {
        System.out.println("Sending email with message: " + message);
    }
}

class SMSNotification implements Notification {
    @Override
    public void send(String message) {
        System.out.println("Sending SMS with message: " + message);
    }
}

// Creator
abstract class NotificationFactory {
    public abstract Notification createNotification();
}

class EmailNotificationFactory extends NotificationFactory {
    @Override
    public Notification createNotification() {
        return new EmailNotification();
    }
}

class SMSNotificationFactory extends NotificationFactory {
    @Override
    public Notification createNotification() {
        return new SMSNotification();
    }
}

public class FactoryMethodPattern {
    public static void main(String[] args) {
        NotificationFactory emailFactory = new EmailNotificationFactory();
        Notification emailNotification = emailFactory.createNotification();
        emailNotification.send("Hello via Email!");

        NotificationFactory smsFactory = new SMSNotificationFactory();
        Notification smsNotification = smsFactory.createNotification();
        smsNotification.send("Hello via SMS!");
    }
}

