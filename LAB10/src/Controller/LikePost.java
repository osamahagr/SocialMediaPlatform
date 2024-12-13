package Controller;

import Model.Database;
import Model.Post;
import Model.User;
import View.Alert;
import org.json.JSONArray;
import org.json.JSONObject;

public class LikePost {
    private User user;
    private Post post;
    private Database database;

    public LikePost(User user, Post post, Database database) {
        this.user = user;
        this.post = post;
        this.database = database;
    }

    public boolean isLiked() {
        boolean liked = false;
        try {
            // Retrieve all likes from the database
            JSONArray likes = database.getLikes();

            // Check if the post is already liked by the user
            for (Object obj : likes) {
                JSONObject likeJson = (JSONObject) obj;
                if (likeJson.getString("userId").equals(user.getUserID()) &&
                        likeJson.getString("postId").equals(post.getContentId())) {
                    return true; // The post is already liked by the user
                }
            }

            // If not liked, create a new like and add it to the database
            JSONObject newLike = new JSONObject();
            newLike.put("userId", user.getUserID());
            newLike.put("postId", post.getContentId());

            likes.put(newLike);
            database.saveLikes(likes);

            liked = true;
        } catch (Exception e) {
            new Alert("Error liking post: " + e.getMessage(), null);
        }
        return liked;
    }

    public boolean removeLike() {
        boolean unliked = false;
        try {
            JSONArray likes = database.getLikes();

            // Find and remove the like from the list
            for (int i = 0; i < likes.length(); i++) {
                JSONObject likeJson = likes.getJSONObject(i);
                if (likeJson.getString("userId").equals(user.getUserID()) &&
                        likeJson.getString("postId").equals(post.getContentId())) {
                    likes.remove(i);  // Remove the like from the array
                    unliked = true;
                    break;
                }
            }

            if (unliked) {
                database.saveLikes(likes);  // Save the updated likes list
            }

        } catch (Exception e) {
            new Alert("Error removing like from post: " + e.getMessage(), null);
        }
        return unliked;
    }
}
