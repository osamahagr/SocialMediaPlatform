package Controller;

import Model.Comment;
import Model.Database;
import Model.Post;
import Model.User;
import View.Alert;
import org.json.JSONArray;
import org.json.JSONObject;

public class CreateComment {
    private Comment comment;
    private Post post;
    private User user;
    private Database database;

    public CreateComment(Comment comment, Post post, User user, Database database) {
        this.comment = comment;
        this.post = post;
        this.user = user;
        this.database = database;
    }

    public boolean addComment() {
        boolean success = false;
        try {
            // Create a JSON object for the new comment
            JSONObject newComment = new JSONObject();
            newComment.put("commentId", comment.getCommentID()); // Ensure ID matches Comment class
            newComment.put("content", comment.getContent());
            newComment.put("authorID", user.getUserID());
            newComment.put("timestamp", comment.getDateTimeToString());
            newComment.put("postId", comment.getPostID());

            // Add the comment to the database
            JSONArray comments = database.getComments();
            comments.put(newComment);
            database.saveComments(comments);
            success = true;
        } catch (Exception e) {
            new Alert("Error adding comment: " + e.getMessage(), null);
        }
        return success;
    }
}
