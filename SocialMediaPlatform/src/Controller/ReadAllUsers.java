package Controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Model.Database;
import Model.User;
import View.Alert;
import org.json.JSONArray;
import org.json.JSONObject;


public class ReadAllUsers {
    private ArrayList<User> users;

    public ReadAllUsers(Database database, User currentUser) {
        users = new ArrayList<>();
        try {
            JSONArray allUsers = database.getUsers();
            for (Object obj : allUsers) {
                JSONObject userJson = (JSONObject) obj;
                User user = new User();
                user.setUserID(userJson.getString("userId"));
                user.setFirstName(userJson.getString("firstName"));
                user.setLastName(userJson.getString("lastName"));
                user.setEmail(userJson.getString("email"));

                // Exclude the current user from the list
                if (!user.getUserID().equals(currentUser.getUserID())) {
                    users.add(user);
                }
            }
        } catch (Exception e) {
            new Alert("Error reading users: " + e.getMessage(), null);
        }
    }

    public ArrayList<User> getList() {
        return users;
    }
}
