package Controller;

import Model.Group;
import org.json.JSONArray;
import org.json.JSONObject;
import Model.Database;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

public class GroupController {

    private Database database;

    public GroupController(Database database) {
        this.database = database;
    }

    // Create a new group
    public Group createGroup(String userId, String name, String description, String photo) {
        Group newGroup = new Group(name, description, photo);
        JSONObject creator = new JSONObject();
        creator.put("userId", userId);
        creator.put("role", "primary_admin");
    newGroup.addMember(userId, "primary_admin");  

        JSONObject groupJson = newGroup.toJSONObject();
        database.createGroup(groupJson); 
        return newGroup;
    }

    // Add a post to a group (Only Admins and Primary Admin can manage posts)
    public void addPostToGroup(String groupId, String authorId, String content) {
    Optional<Group> groupOpt = getGroupById(groupId);
    if (groupOpt.isPresent()) {
        Group group = groupOpt.get();
        // Validate that the user is a member of the group
        boolean isMember = false;
        for (int i = 0; i < group.getMembers().length(); i++) {
            JSONObject member = group.getMembers().getJSONObject(i);
            if (member.getString("userId").equals(authorId)) {
                isMember = true;
                break;
            }
        }

        if (!isMember) {
            System.out.println("Error: User is not a member of the group.");
            return;
        }

        JSONObject newPost = new JSONObject();
        newPost.put("contentId", java.util.UUID.randomUUID().toString());
        newPost.put("authorId", authorId);
        newPost.put("content", content);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String currentTime = LocalDateTime.now().format(formatter);
        newPost.put("timestamp", currentTime);
        group.addPost(newPost);

        database.updateGroup(group.toJSONObject());
        System.out.println("Post added successfully.");
    } else {
        System.out.println("Error: Group not found.");
    }
}


   public void editPostInGroup(String groupId, String userId, int postIndex, String newContent) {
    Optional<Group> groupOpt = getGroupById(groupId);
    if (groupOpt.isPresent()) {
        Group group = groupOpt.get();

        if (group.isAdmin(userId) || group.isPrimaryAdmin(userId)) {
            if (postIndex >= 0 && postIndex < group.getPosts().length()) {
                JSONObject existingPost = group.getPosts().getJSONObject(postIndex);

                // Update the content of the post
                JSONObject updatedPost = new JSONObject(existingPost.toString());
                updatedPost.put("content", newContent);

                group.editPost(postIndex, updatedPost);
                database.updateGroup(group.toJSONObject()); // Update the group in the database
                System.out.println("Post updated successfully.");
            } else {
                System.out.println("Error: Post index out of bounds.");
            }
        } else {
            System.out.println("Error: Only Admins or Primary Admin can edit posts.");
        }
    } else {
        System.out.println("Error: Group not found.");
    }
}


    // Delete a post (Only Admins and Primary Admin can delete posts)
    public void deletePostFromGroup(String groupId, String userId, int postIndex) {
    Optional<Group> groupOpt = getGroupById(groupId);
    if (groupOpt.isPresent()) {
        Group group = groupOpt.get();

        // Check if the user is either an Admin or the Primary Admin
        if (group.isAdmin(userId) || group.isPrimaryAdmin(userId)) {
            if (postIndex >= 0 && postIndex < group.getPosts().length()) {
                group.deletePost(postIndex);
                database.updateGroup(group.toJSONObject()); // Update the group in the database
                System.out.println("Post deleted successfully.");
            } else {
                System.out.println("Error: Post index out of bounds.");
            }
        } else {
            System.out.println("Error: Only Admins or Primary Admin can delete posts.");
        }
    } else {
        System.out.println("Error: Group not found.");
    }
}


    public void approveMembership(String groupId, String userId, String adminId) {
        Optional<Group> groupOpt = getGroupById(groupId);
        if (groupOpt.isPresent()) {
            Group group = groupOpt.get();
            if (group.isAdmin(adminId)) {
                group.approveMembershipRequest(userId);
                database.updateGroup(group.toJSONObject()); // Update the group in the database
            }
        }
    }

    public void declineMembership(String groupId, String userId, String adminId) {
        Optional<Group> groupOpt = getGroupById(groupId);
        if (groupOpt.isPresent()) {
            Group group = groupOpt.get();
            if (group.isAdmin(adminId)) {
                group.declineMembershipRequest(userId);
                database.updateGroup(group.toJSONObject()); // Update the group in the database
            }
        }
    }

    // Remove a member Only Admins and Primary Admin can remove members
    public void removeMember(String groupId, String userId, String adminId) {
        Optional<Group> groupOpt = getGroupById(groupId);
        if (groupOpt.isPresent()) {
            Group group = groupOpt.get();
            if (group.isAdmin(adminId)) {
                group.removeMember(userId);
                database.updateGroup(group.toJSONObject()); // Update the group in the database
            }
        }
    }

    // Leave the group (Normal Users can leave the group)
    public void leaveGroup(String groupId, String userId) {
        Optional<Group> groupOpt = getGroupById(groupId);
        if (groupOpt.isPresent()) {
            Group group = groupOpt.get();
            group.leaveGroup(userId);
            database.updateGroup(group.toJSONObject()); // Update the group in the database
        }
    }

    // Get a group by its ID
    public Optional<Group> getGroupById(String groupId) {
        Optional<JSONObject> groupJsonOpt = database.getGroupById(groupId);
        if (groupJsonOpt.isPresent()) {
            JSONObject groupJson = groupJsonOpt.get();
            Group group = new Group(groupJson.getString("name"), groupJson.getString("description"), groupJson.getString("photo"));
            group.setGroupId(groupJson.getString("groupId"));
            group.setMembers(groupJson.getJSONArray("members"));
            group.setPosts(groupJson.getJSONArray("posts"));
            group.setMembershipRequests(groupJson.getJSONArray("membershipRequests"));
            return Optional.of(group);
        }
        return Optional.empty();
    }

    // Delete a group
    public void deleteGroup(String groupId) {
        database.deleteGroup(groupId); // Delete the group from the database
    }
public void addMemberToGroup(String groupId, String userId, String role, String adminId) {
    Optional<Group> groupOpt = getGroupById(groupId);
    if (groupOpt.isPresent()) {
        Group group = groupOpt.get();

        if (isUserAdmin(group, adminId)) {
            boolean isAlreadyMember = false;
            for (int i = 0; i < group.getMembers().length(); i++) {
                JSONObject member = group.getMembers().getJSONObject(i);
                if (member.getString("userId").equals(userId)) {
                    isAlreadyMember = true;
                    break;
                }
            }

            if (isAlreadyMember) {
                System.out.println("Error: User is already a member of the group.");
                return;
            }
            group.addMember(userId, role); 
            database.updateGroup(group.toJSONObject()); 
            System.out.println("Member added to group: " + userId + " with role: " + role);
        } else {
            System.out.println("Error: Only admins or primary admin can add members.");
        }
    } else {
        System.out.println("Error: Group not found.");
    }
}



private boolean isUserAdmin(Group group, String userId) {
    for (int i = 0; i < group.getMembers().length(); i++) {
        JSONObject member = group.getMembers().getJSONObject(i);
        if (member.getString("userId").equals(userId) && 
            (member.getString("role").equals("primary_admin") || member.getString("role").equals("Admin"))) {
            return true;
        }
    }
    return false;
}

}
