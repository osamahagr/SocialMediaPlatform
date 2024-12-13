package Controller;

import java.util.ArrayList;

import Model.Database;
import Model.Post;
import Model.User;
import View.Alert;
import org.json.JSONArray;
import org.json.JSONObject;

public class ReadUserPosts {

    private ArrayList<Post> posts;

    public ReadUserPosts(User u, Database database) {
        posts = new ArrayList<>();
        try {
            // Get all posts from the database
            JSONArray postsArray = database.getPosts();
            
            // Filter posts by user ID
            for (Object obj : postsArray) {
                JSONObject postObj = (JSONObject) obj;
                if (postObj.getString("authorId").equals(u.getUserID())) {
                    Post post = new Post();
                    post.setContentId(postObj.getString("contentId"));
                    post.setContent(postObj.getString("content"));
                    post.setDateTimeFromString(postObj.getString("timestamp"));
                    post.setUser(u);
                    posts.add(post);
                }
            }
        } catch (Exception e) {
            new Alert("Error reading user posts: " + e.getMessage(), null);
        }
    }

    public ArrayList<Post> getPosts() {
        return posts;
    }
}
