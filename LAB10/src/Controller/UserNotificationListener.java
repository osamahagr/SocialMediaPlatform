/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

public class UserNotificationListener implements NotificationListener {
    private final String userId;

    public UserNotificationListener(String userId) {
        this.userId = userId;
    }

    @Override
    public void update(Notification notification) {
        if (notification.getRecipientId().equals(userId)) {
            System.out.println("Notification for User " + userId + ": " + notification.getMessage());
        }
    }
}

