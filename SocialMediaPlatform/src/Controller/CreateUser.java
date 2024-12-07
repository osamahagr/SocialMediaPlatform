package Controller;

import Model.Database;
import Model.User;
import View.Alert;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CreateUser {
    private User user;
    private Database database;

    public CreateUser(User user, Database database) {
        this.user = user;
        this.database = database;
    }

    public void create() {
        try {
            if (isEmailUsed()) {
                new Alert("This email has been used before", null);
                return;
            }

            JSONObject newUser = new JSONObject();
            newUser.put("userId", user.getUserID());
            newUser.put("username", user.getUserName());
            newUser.put("firstName", user.getFirstName());
            newUser.put("lastName", user.getLastName());
            newUser.put("email", user.getEmail());
            newUser.put("hashedPassword", user.getHashedPassword());
            newUser.put("dateOfBirth", user.getDateOfBirth());
            newUser.put("bio", user.getBio());
            newUser.put("profilePhotoPath", user.getProfilePhotoPath());
            newUser.put("coverPhotoPath", user.getCoverPhotoPath());
            newUser.put("status", user.getStatus());

            newUser.put("friendsIDs", new JSONArray(user.getFriendsIDs()));
            newUser.put("pendingRequests", new JSONArray(user.getPendingRequests()));
            newUser.put("likesIDs", new JSONArray(user.getLikesIDs()));

            database.addUser(newUser);
        } catch (Exception e) {
            e.printStackTrace();
            new Alert("Error creating user: " + e.getMessage(), null);
        }
    }

    public boolean isEmailUsed() {
        try {
            if (database == null || database.getUsers() == null) {
                new Alert("Database or user list is null.", null);
                return false;
            }
            for (Object obj : database.getUsers()) {
                if (obj instanceof JSONObject) {
                    JSONObject existingUser = (JSONObject) obj;
                    if (existingUser.has("email") && existingUser.getString("email").equalsIgnoreCase(user.getEmail())) {
                        return true;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            new Alert("Error checking email: " + e.getMessage(), null);
        }
        return false;
    }

    public User getUser(String email, String hashedPassword) {
        try {
            for (Object obj : database.getUsers()) {
                if (obj instanceof JSONObject) {
                    JSONObject existingUser = (JSONObject) obj;
                    if (existingUser.getString("email").equalsIgnoreCase(email) &&
                            existingUser.getString("hashedPassword").equals(hashedPassword)) {
                        User foundUser = new User();
                        foundUser.setUserID(existingUser.getString("userId"));
                        foundUser.setUserName(existingUser.getString("username"));
                        foundUser.setFirstName(existingUser.getString("firstName"));
                        foundUser.setLastName(existingUser.getString("lastName"));
                        foundUser.setEmail(existingUser.getString("email"));
                        foundUser.setBio(existingUser.getString("bio"));
                        foundUser.setStatus(existingUser.getString("status"));
                        foundUser.setProfilePhotoPath(existingUser.getString("profilePhotoPath"));
                        foundUser.setCoverPhotoPath(existingUser.getString("coverPhotoPath"));
                        foundUser.setDateOfBirth(existingUser.getString("dateOfBirth"));
                        foundUser.setHashedPassword(existingUser.getString("hashedPassword"));
                        foundUser.setFriendsIDs((ArrayList<String>) jsonArrayToList(existingUser.getJSONArray("friendsIDs")));
                        foundUser.setPendingRequests((ArrayList<String>) jsonArrayToList(existingUser.getJSONArray("pendingRequests")));
                        foundUser.setLikesIDs((ArrayList<String>) jsonArrayToList(existingUser.getJSONArray("likesIDs")));
                        return foundUser;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            new Alert("Error retrieving user: " + e.getMessage(), null);
        }
        return null;
    }

    private List<String> jsonArrayToList(JSONArray jsonArray) {
        List<String> list = new ArrayList<>();
        for (Object obj : jsonArray) {
            list.add(obj.toString());
        }
        return list;
    }
}
