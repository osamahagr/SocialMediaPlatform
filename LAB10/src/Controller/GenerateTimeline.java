package Controller;

import Model.Database;
import Model.Post;
import Model.User;
import View.Alert;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.json.JSONArray;
import org.json.JSONObject;

public class GenerateTimeline {
    private ArrayList<Post> posts;

    public GenerateTimeline(User user, Database database) {
        posts = new ArrayList<>();
        try {
            // Get all posts from the database as a JSONArray
            JSONArray allPosts = database.getPosts();

            // Get the IDs of the user's friends, including the user itself
            List<String> userAndFriendsIDs = new ArrayList<>();
            userAndFriendsIDs.add(user.getUserID()); // Add the user's own ID
            userAndFriendsIDs.addAll(database.getFriendsIDs(user.getUserID()));

            // Iterate through all posts
            for (Object obj : allPosts) {
                // Ensure the object is a valid JSONObject
//                if (!(obj instanceof JSONObject)) {
//                    continue; // Skip invalid entries
//                }

                JSONObject postJson = (JSONObject) obj;

                // Check if the post belongs to the user or one of the user's friends
                String userId = postJson.optString("authorId", "");
                if (userAndFriendsIDs.contains(userId)) {
                    Post post = new Post();
                    post.setContentId(postJson.optString("contentId")); // Set post ID
                    post.setContent(postJson.optString("content", "")); // Set post content

                    // Parse and set the post's date and time
                    String dateTimeString = postJson.optString("timestamp", "");
                    if (!dateTimeString.isEmpty()) {
                        try {
                            post.setDateTimeFromString(dateTimeString);
                        } catch (Exception e) {
                            post.setDateTimeFromString(null); 
                        }
                    } else {
                        post.setDateTimeFromString(null); 
                    }

                    // Find the user associated with the post
                    Optional<JSONObject> userJsonOptional = database.findUserById(userId);
                    if (userJsonOptional.isPresent()) {
                        JSONObject userJson = userJsonOptional.get();
                        User postUser = new User();
                        postUser.setUserID(userJson.optString("userId"));
                        postUser.setFirstName(userJson.optString("firstName"));
                        postUser.setLastName(userJson.optString("lastName"));
                        post.setUser(postUser); // Attach user information to the post
                    } else {
                        post.setUser(null); // Handle missing user data
                    }

                    // Add the post to the list
                    posts.add(post);
                }
            }
        } catch (Exception e) {
            new Alert("Error generating timeline: " + e.getMessage(), null); // Handle errors
        }
    }

    public ArrayList<Post> getPosts() {
        
        return posts; 
    }
}
