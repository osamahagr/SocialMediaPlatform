/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;


import java.util.ArrayList;
import java.util.List;

public class NotificationService {
    private final List<NotificationListener> listeners = new ArrayList<>();

    public void addListener(NotificationListener listener) {
        listeners.add(listener);
    }

    public void notify(Notification notification) {
        for (NotificationListener listener : listeners) {
            listener.update(notification);
        }
    }
}
