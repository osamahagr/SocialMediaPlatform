package Controller;

import Model.Database;
import Model.User;
import View.Alert;
import org.json.JSONArray;
import org.json.JSONObject;

public class RemoveFriend {

    private User user, friend;
    private Database database;

    public RemoveFriend(User user, Database database, User friend) {
        this.user = user;
        this.friend = friend;
        this.database = database;
    }

    public boolean isRemoved() {
        boolean removed = false;
        try {
            JSONArray friends = database.getFriends(user.getUserID());
            JSONArray updatedFriends = new JSONArray();
            
            // Filter out the friend from the friends list
            for (Object obj : friends) {
                JSONObject friendObj = (JSONObject) obj;
                if (!friendObj.getString("friendId").equals(friend.getUserID())) {
                    updatedFriends.put(friendObj);
                }
            }
            
            // Save the updated list back to the JSON file
            database.saveFriends(updatedFriends);
            removed = true;
        } catch (Exception e) {
            new Alert("Error removing friend: " + e.getMessage(), null);
            removed = false;
        }
        return removed;
    }
}
