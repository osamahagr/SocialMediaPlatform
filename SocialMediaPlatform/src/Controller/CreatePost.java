package Controller;

import Model.Database;
import Model.Post;
import View.Alert;
import org.json.JSONArray;
import org.json.JSONObject;

public class CreatePost {
    private Post post;
    private Database database;

    public CreatePost(Post post, Database database) {
        this.post = post;
        this.database = database;
    }

    public boolean addPost() {
        boolean success = false;
        try {
            // Create a new JSONObject to represent the post
            JSONObject newPost = new JSONObject();
            newPost.put("contentId", post.getContentId());  // Use contentId as unique identifier
            newPost.put("authorId", post.getAuthorId());
            newPost.put("content", post.getContent());
            newPost.put("imagePath", post.getImagePath());
            newPost.put("timestamp", post.getTimestampAsString());

            // Retrieve all existing posts from the database
            JSONArray posts = database.getPosts();
            posts.put(newPost);  // Add the new post to the posts list

            // Save the updated posts list to the database
            database.savePosts(posts);
            success = true;  // Indicate that the post was added successfully
        } catch (Exception e) {
            new Alert("Error adding post: " + e.getMessage(), null);
        }
        return success;  // Return whether the operation was successful
    }
}
