package Controller;

import java.sql.ResultSet;
import java.sql.SQLException;

import Model.Database;
import Model.Post;
import Model.User;
import View.Alert;
import org.json.JSONObject;




public class ReadPostByID {
    private Post post;

    public ReadPostByID(String postId, Database database) {
        try {
            JSONObject postJson = database.findPostById(postId).orElse(null);
            if (postJson != null) {
                post = new Post();
                post.setContentId(postId);
                post.setContent(postJson.getString("content"));
                post.setDateTimeFromString(postJson.getString("timestamp"));

                database.findUserById(postJson.getString("authorId")).ifPresent(userJson -> {
                    User user = new User();
                    user.setUserID(userJson.getString("userId"));
                    user.setFirstName(userJson.getString("firstName"));
                    user.setLastName(userJson.getString("lastName"));
                    post.setUser(user);
                });
            }
        } catch (Exception e) {
            new Alert("Error reading post by ID: " + e.getMessage(), null);
        }
    }

    public Post getPost() {
        return post;
    }
}
