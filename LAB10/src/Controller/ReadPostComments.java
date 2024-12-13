package Controller;

import Model.Comment;
import Model.Database;
import Model.Post;
import Model.User;
import View.Alert;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class ReadPostComments {
    private int commentsCounter;
    private ArrayList<Comment> comments;

    public ReadPostComments(Post post, Database database) {
        comments = new ArrayList<>();
        commentsCounter = 0;
        try {
            JSONArray allComments = database.getComments();
            for (Object obj : allComments) {
                JSONObject commentJson = (JSONObject) obj;
                if (commentJson.getString("postId").equals(post.getContentId())) {
                    Comment comment = new Comment();
                    comment.setContent(commentJson.getString("content"));
                    comment.setDateTimeFromString(commentJson.getString("timestamp"));

                    database.findUserById(commentJson.getString("authorID")).ifPresent(userJson -> {
                        User user = new User();
                        user.setUserID(userJson.getString("userId"));
                        user.setFirstName(userJson.getString("firstName"));
                        user.setLastName(userJson.getString("lastName"));
                        comment.setUser(user);
                    });

                    comments.add(comment);
                    commentsCounter++;
                }
            }
        } catch (Exception e) {
            new Alert("Error reading post comments: " + e.getMessage(), null);
        }
    }

    public int getCommentsCount() {
        return commentsCounter;
    }

    public ArrayList<Comment> getComments() {
        return comments;
    }
}
