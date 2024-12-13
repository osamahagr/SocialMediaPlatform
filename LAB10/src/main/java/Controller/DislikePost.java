package Controller;

import Model.Database;
import Model.Post;
import Model.User;
import View.Alert;
import org.json.JSONArray;
import org.json.JSONObject;

public class DislikePost {
    private User user;
    private Post post;
    private Database database;

    public DislikePost(User user, Post post, Database database) {
        this.user = user;
        this.post = post;
        this.database = database;
    }

    public boolean removeLike() {
        boolean success = false;
        try {
            JSONArray likes = database.getLikes();  // Retrieve the likes from the database
            for (int i = 0; i < likes.length(); i++) {
                JSONObject like = likes.getJSONObject(i);
                // Check if the like matches the current user and the content (post or story)
                if (like.getString("userId").equals(user.getUserID()) &&
                        like.getString("postId").equals(post.getContentId())) {
                    likes.remove(i);  // Remove the like
                    break;
                }
            }
            database.saveLikes(likes);  // Save the updated likes array
            success = true;  // Indicate that the like was successfully removed
        } catch (Exception e) {
            new Alert("Error removing like: " + e.getMessage(), null);
        }
        return success;  // Return whether the operation was successful
    }
}
