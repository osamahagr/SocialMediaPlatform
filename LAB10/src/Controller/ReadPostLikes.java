package Controller;

import java.sql.ResultSet;
import java.sql.SQLException;

import Model.Database;
import Model.Post;
import View.Alert;
import org.json.JSONArray;
import org.json.JSONObject;

public class ReadPostLikes {
    private int likes;

    public ReadPostLikes(Post post, Database database) {
        likes = 0;
        try {
            JSONArray allLikes = database.getLikes();
            for (Object obj : allLikes) {
                JSONObject likeJson = (JSONObject) obj;
                if (likeJson.getString("postId").equals(post.getContentId())) {
                    likes++;
                }
            }
        } catch (Exception e) {
            new Alert("Error reading post likes: " + e.getMessage(), null);
        }
    }

    public int getLikesCount() {
        return likes;
    }
}
