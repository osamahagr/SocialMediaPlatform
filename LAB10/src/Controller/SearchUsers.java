/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Model.Database;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author moaz1
 */
public class SearchUsers {
    private final Database database ;

    public SearchUsers(Database database) {
        this.database=database;
    }

    // Search for users by username
    public List<JSONObject> searchUsers(String usernameQuery) {
        List<JSONObject> matchingUsers = new ArrayList<>();
        JSONArray users = database.getUsers();
        for (Object userObj : users) {
            JSONObject user = (JSONObject) userObj;
            if (user.getString("username").toLowerCase().contains(usernameQuery.toLowerCase())) {
                // Return basic user information
                JSONObject userBasicInfo = new JSONObject();
                userBasicInfo.put("username", user.getString("username"));
                userBasicInfo.put("status", user.optString("status", "N/A"));
                matchingUsers.add(userBasicInfo);
            }
        }
        return matchingUsers;
    }
}
