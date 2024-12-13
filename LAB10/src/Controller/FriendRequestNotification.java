
package Controller;

public class FriendRequestNotification implements Notification {
    private final String senderId;
    private final String recipientId;

    public FriendRequestNotification(String senderId, String recipientId) {
        this.senderId = senderId;
        this.recipientId = recipientId;
    }

    @Override
    public String getMessage() {
        return "You have a new friend request from User " + senderId;
    }

    @Override
    public String getRecipientId() {
        return recipientId;
    }

    @Override
    public String getType() {
        return "FRIEND_REQUEST";
    }
}

