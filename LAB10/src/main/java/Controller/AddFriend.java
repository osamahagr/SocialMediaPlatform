package Controller;

import Model.Database;
import Model.User;
import View.Alert;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Optional;

public class AddFriend {

    private User user, friend;
    private Database database;

    public AddFriend(User user, Database database, User friend) {
        this.user = user;
        this.database = database;
        this.friend = friend;
    }

    public boolean isAdded() {
        boolean added = false;

        // Find the user and the friend in the database
        Optional<JSONObject> userOpt = database.findUserById(user.getUserID());
        Optional<JSONObject> friendOpt = database.findUserById(friend.getUserID());

        if (userOpt.isPresent() && friendOpt.isPresent()) {
            JSONObject userObj = userOpt.get();
            JSONObject friendObj = friendOpt.get();

            // Add friendship relation to friends.json
            database.addFriend(user.getUserID(), friend.getUserID());

            JSONArray userFriendsIDs = userObj.optJSONArray("friendsIDs");
            if (userFriendsIDs == null) {
                userFriendsIDs = new JSONArray();
            }

            if (!userFriendsIDs.toList().contains(friend.getUserID())) {
                userFriendsIDs.put(friend.getUserID());
                userObj.put("friendsIDs", userFriendsIDs);
                database.updateUser(userObj);
            }

            // Update friend's friendsIDs in users.json
            JSONArray friendFriendsIDs = friendObj.optJSONArray("friendsIDs");
            if (friendFriendsIDs == null) {
                friendFriendsIDs = new JSONArray();
            }

            if (!friendFriendsIDs.toList().contains(user.getUserID())) {
                friendFriendsIDs.put(user.getUserID());
                friendObj.put("friendsIDs", friendFriendsIDs);
                database.updateUser(friendObj);
            }

            added = true;
        } else {
            new Alert("User or friend not found.", null);
        }

        return added;
    }
}
