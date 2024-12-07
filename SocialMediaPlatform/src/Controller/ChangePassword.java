package Controller;

import Model.Database;
import View.Alert;
import org.json.JSONObject;

public class ChangePassword {

    private String password;
    private String userId;
    private Database database;

    public ChangePassword(String password, String userId, Database database) {
        this.userId = userId;
        this.password = password;
        this.database = database;
    }

    public boolean change() {
        boolean changed = false;

        // Retrieve the user object from the database
        JSONObject userObj = database.findUserById(userId).orElse(null);

        if (userObj != null) {
            // Update the password field
            userObj.put("hashedpassword", password);

            // Save the updated user back to the database
            database.updateUser(userObj);
            changed = true;
        } else {
            new Alert("User not found.", null);
        }

        return changed;
    }
}
