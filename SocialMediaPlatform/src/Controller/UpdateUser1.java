package Controller;

import Model.Database;
import Model.User;
import View.Alert;
import org.json.JSONArray;
import org.json.JSONObject;

public class UpdateUser1 {

    private Database database;
    private User user;

    public UpdateUser1(User user, Database database) {
        this.database = database;
        this.user = user;
    }

    public boolean isEmailUsed() {
        // Assuming there's a method in CreateUser to check for email duplication
        return new CreateUser(user, database).isEmailUsed();
    }

    public boolean update() {
        boolean updated = false;
        try {
            JSONArray users = database.getUsers();
            // Find the user and update their details
            for (int i = 0; i < users.length(); i++) {
                JSONObject userObj = users.getJSONObject(i);
                if (userObj.getString("userId").equals(user.getUserID())) {
                    userObj.put("firstName", user.getFirstName());
                    userObj.put("lastName", user.getLastName());
                    userObj.put("email", user.getEmail());
                    users.put(i, userObj);
                    break;
                }
            }
            
            // Save the updated users list back to the JSON file
            database.saveUsers(users);
            updated = true;
        } catch (Exception e) {
            new Alert("Error updating user: " + e.getMessage(), null);
            updated = false;
        }
        return updated;
    }
}
