package Controller;

import java.util.ArrayList;

import Model.Database;
import Model.User;
import View.Alert;
import org.json.JSONArray;
import org.json.JSONObject;

public class ReadUser {
    private boolean loggedIn;
    private User user;

    public ReadUser(String email, String password, Database database) {
        loggedIn = false;
        try {
            String hashedInputPassword = PasswordHasher.hashPassword(password);
            for (Object obj : database.getUsers()) {
                JSONObject userJson = (JSONObject) obj;
                if (userJson.getString("email").equals(email) &&
                        userJson.getString("hashedPassword").equals(hashedInputPassword)) {

                    loggedIn = true;

                    user = new User();

                    // Map all attributes from JSON to User object
                    user.setUserID(userJson.getString("userId"));
                    user.setFirstName(userJson.optString("firstName", ""));
                    user.setLastName(userJson.optString("lastName", ""));
                    user.setEmail(userJson.optString("email", ""));
                    user.setHashedPassword(userJson.optString("hashedPassword", ""));
                    user.setBio(userJson.optString("bio", ""));
                    user.setDateOfBirth(userJson.optString("dateOfBirth", ""));
                    user.setCoverPhotoPath(userJson.optString("coverPhotoPath", ""));
                    user.setProfilePhotoPath(userJson.optString("profilePhotoPath", ""));
                    user.setStatus(userJson.optString("status", "offline"));
                    user.setUserName(userJson.optString("username", ""));
                    
                    // Map friends, likes, and pending requests
                    user.setFriendsIDs(jsonArrayToList(userJson.optJSONArray("friendsIDs")));
                    user.setLikesIDs(jsonArrayToList(userJson.optJSONArray("likesIDs")));
                    user.setPendingRequests(jsonArrayToList(userJson.optJSONArray("pendingRequests")));

                    break;
                }
            }
        } catch (Exception e) {
            new Alert("Error reading user: " + e.getMessage(), null);
        }
    }

    public boolean loggedIn() {
        return loggedIn;
    }

    public User getUser() {
        return user;
    }

    // Helper method to convert JSONArray to ArrayList<String>
    private ArrayList<String> jsonArrayToList(JSONArray jsonArray) {
        ArrayList<String> list = new ArrayList<>();
        if (jsonArray != null) {
            for (int i = 0; i < jsonArray.length(); i++) {
                list.add(jsonArray.getString(i));
            }
        }
        return list;
    }
}
