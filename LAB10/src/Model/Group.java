package Model;

import org.json.JSONArray;
import org.json.JSONObject;

public class Group {

    private String groupId;
    private String name;
    private String description;
    private String photo;
    private JSONArray members;
    private JSONArray posts;  // New attribute to store posts
    private JSONArray membershipRequests;  // New attribute to store membership requests

    // Constructor to create a new group
    public Group(String name, String description, String photo) {
        this.groupId = java.util.UUID.randomUUID().toString();
        this.name = name;
        this.description = description;
        this.photo = photo;
        this.members = new JSONArray();
        this.posts = new JSONArray();
        this.membershipRequests = new JSONArray();
    }

    // Getters and Setters
    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public JSONArray getMembers() {
        return members;
    }

    public void setMembers(JSONArray members) {
        this.members = members;
    }

    public JSONArray getPosts() {
        return posts;
    }

    public void setPosts(JSONArray posts) {
        this.posts = posts;
    }

    public JSONArray getMembershipRequests() {
        return membershipRequests;
    }

    public void setMembershipRequests(JSONArray membershipRequests) {
        this.membershipRequests = membershipRequests;
    }
   // Inside Group class

// Add member to the group
public void addMember(String userId, String role) {
    JSONObject newMember = new JSONObject();
    newMember.put("userId", userId);
    newMember.put("role", role);
    this.members.put(newMember);
}

// Remove member by userId
public void removeMember(String userId) {
    for (int i = 0; i < members.length(); i++) {
        JSONObject member = members.getJSONObject(i);
        if (member.getString("userId").equals(userId)) {
            members.remove(i);
            break;
        }
    }
}

    // Add a post to the group
    public void addPost(JSONObject post) {
        this.posts.put(post);
    }

    // Edit a post by index
    public void editPost(int index, JSONObject updatedPost) {
        if (index >= 0 && index < posts.length()) {
            posts.put(index, updatedPost);
        }
    }

    // Delete a post by index
    public void deletePost(int index) {
        if (index >= 0 && index < posts.length()) {
            posts.remove(index);
        }
    }

    // Add membership request
    public void addMembershipRequest(JSONObject request) {
        this.membershipRequests.put(request);
    }

    // Approve a membership request
    public void approveMembershipRequest(String userId) {
        for (int i = 0; i < membershipRequests.length(); i++) {
            JSONObject request = membershipRequests.getJSONObject(i);
            if (request.getString("userId").equals(userId)) {
                // Move the request to members
                JSONObject newMember = new JSONObject();
                newMember.put("userId", userId);
                newMember.put("role", "member");
                members.put(newMember);
                membershipRequests.remove(i);
                break;
            }
        }
    }

    // Decline a membership request
    public void declineMembershipRequest(String userId) {
        for (int i = 0; i < membershipRequests.length(); i++) {
            JSONObject request = membershipRequests.getJSONObject(i);
            if (request.getString("userId").equals(userId)) {
                membershipRequests.remove(i);
                break;
            }
        }
    }

   

    // Leave the group
    public void leaveGroup(String userId) {
        removeMember(userId);
    }

    // Check if the user is the Primary Admin
    public boolean isPrimaryAdmin(String userId) {
        JSONObject primaryAdmin = members.getJSONObject(0); // Assuming the first member is the primary admin
        return primaryAdmin.getString("userId").equals(userId);
    }

    // Check if the user is an Admin
    public boolean isAdmin(String userId) {
        for (int i = 1; i < members.length(); i++) {
            JSONObject member = members.getJSONObject(i);
            if (member.getString("userId").equals(userId) && (member.getString("role").equals("admin")) || member.getString("role").equals("primary_admin")){
                return true;
            }
        }
        return false;
    }

    public JSONObject toJSONObject() {
        JSONObject groupJson = new JSONObject();
        groupJson.put("groupId", this.groupId);
        groupJson.put("name", this.name);
        groupJson.put("description", this.description);
        groupJson.put("photo", this.photo);
        groupJson.put("members", this.members);
        groupJson.put("posts", this.posts);
        groupJson.put("membershipRequests", this.membershipRequests);
        return groupJson;
    }
}
