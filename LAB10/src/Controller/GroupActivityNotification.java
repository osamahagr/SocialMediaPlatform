package Controller;

public class GroupActivityNotification implements Notification {
    private final String groupId;
    private final String recipientId;
    private final String activity;

    public GroupActivityNotification(String groupId, String recipientId, String activity) {
        this.groupId = groupId;
        this.recipientId = recipientId;
        this.activity = activity;
    }

    @Override
    public String getMessage() {
        return "Group " + groupId + " has a new activity: " + activity;
    }

    @Override
    public String getRecipientId() {
        return recipientId;
    }

    @Override
    public String getType() {
        return "GROUP_ACTIVITY";
    }
}
