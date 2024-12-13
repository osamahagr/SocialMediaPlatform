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
public class SearchGroups {
    private final Database database;

    public SearchGroups(Database database) {
        this.database=database;
    }

    // Search for groups by group name
    public List<JSONObject> searchGroups(String groupNameQuery) {
        List<JSONObject> matchingGroups = new ArrayList<>();
        JSONArray groups = database.getGroups();
        for (Object groupObj : groups) {
            JSONObject group = (JSONObject) groupObj;
            if (group.getString("name").toLowerCase().contains(groupNameQuery.toLowerCase())) {
                // Return basic group information
                JSONObject groupBasicInfo = new JSONObject();
                groupBasicInfo.put("groupId", group.getString("groupId"));
                groupBasicInfo.put("name", group.getString("name"));
                groupBasicInfo.put("description", group.optString("description", "N/A"));
                matchingGroups.add(groupBasicInfo);
            }
        }
        return matchingGroups;
    }
}