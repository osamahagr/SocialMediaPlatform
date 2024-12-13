package Controller;

import java.util.ArrayList;

import javax.swing.JPanel;

import Model.Comment;
import Model.Database;
import Model.User;
import View.Alert;
import View.JFrame;
import View.Post;
import org.json.JSONArray;
import org.json.JSONObject;

public class ReadUserComments {

    private ArrayList<JPanel> panels;

    public ReadUserComments(User u, Database database, JFrame f) {
        panels = new ArrayList<>();
        try {
            // Get all comments for the user from the database
            JSONArray commentsArray = database.getComments();
            ArrayList<Comment> comments = new ArrayList<>();
            ArrayList<String> postIDs = new ArrayList<>();

            // Iterate through comments and filter by user ID
            for (Object obj : commentsArray) {
                JSONObject commentObj = (JSONObject) obj;
                String userId = commentObj.getString("authorID");
                
                // Check if the comment belongs to the given user
                if (userId.equals(u.getUserID())) {
                    Comment comment = new Comment();
                    comment.setCommentID(commentObj.getString("commentId"));
                    comment.setContent(commentObj.getString("content"));
                    comment.setUser(u);
                    comment.setDateTimeFromString(commentObj.getString("timestamp"));
                    comments.add(comment);
                    postIDs.add(commentObj.getString("postId"));  // Store the post ID associated with the comment
                }
            }

            // For each comment, retrieve the corresponding post details
            for (int i = 0; i < comments.size(); i++) {
                String postId = postIDs.get(i);
                Model.Post post = new ReadPostByID(postId, database).getPost();
                
                // Add a panel for the post and its comment
                panels.add(new Post(u, post, database, f));
                panels.add(new View.Comment(comments.get(i)));
            }
        } catch (Exception e) {
            new Alert("Error reading user comments: " + e.getMessage(), null);
        }
    }

    public ArrayList<JPanel> getPostsWithComments() {
        return panels;
    }
}
