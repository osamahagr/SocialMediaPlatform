package Controller;

import java.util.ArrayList;

import Model.Database;
import Model.Post;
import Model.User;
import View.Alert;
import org.json.JSONArray;
import org.json.JSONObject;

public class ReadUserLikes {

    private ArrayList<Post> posts;

    public ReadUserLikes(User u, Database database) {
        posts = new ArrayList<>();
        try {
            // Get all likes from the database
            JSONArray likes = database.getLikes();
            ArrayList<String> postIDs = new ArrayList<>();
            
            // Collect all liked post IDs for the user
            for (Object obj : likes) {
                JSONObject likeObj = (JSONObject) obj;
                if (likeObj.getString("userId").equals(u.getUserID())) {
                    postIDs.add(likeObj.getString("postId"));
                }
            }
            
            // Fetch the posts for the liked post IDs
            for (String postId : postIDs) {
                Post post = new ReadPostByID(postId, database).getPost();
                posts.add(post);
            }
        } catch (Exception e) {
            new Alert("Error reading user likes: " + e.getMessage(), null);
        }
    }

    public ArrayList<Post> getPosts() {
        return posts;
    }
}
