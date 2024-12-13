/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;


public class NotificationFactory {
    public static Notification createNotification(String type, String... args) {
        switch (type) {
            case "FRIEND_REQUEST":
                return new FriendRequestNotification(args[0], args[1]);
            case "GROUP_ACTIVITY":
                return new GroupActivityNotification(args[0], args[1], args[2]);
            default:
                throw new IllegalArgumentException("Unknown notification type: " + type);
        }
    }
}
