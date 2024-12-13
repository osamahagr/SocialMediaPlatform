package Model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class Comment {

    private String commentID;
    private String postID;
    private String authorID;
    private String content;
    private User user;
    private LocalDateTime dateTime;

    private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    
    	public Comment() {}

    public Comment(String postID, User user, String content) {
        this.commentID = UUID.randomUUID().toString();
        this.postID = postID;
        this.authorID = user.getUserID();
        this.content = content;
        this.dateTime = LocalDateTime.now();
        this.user = user;
    }

    public String getCommentID() {
        return commentID;
    }

    public void setCommentID(String commentID) {
        this.commentID = commentID;
    }

    public User getUser() {
        return user;
    }
    public void setUser(User user)
    {
        this.user = user;
    }

    public String getPostID() {
        return postID;
    }

    public void setPostID(String postID) {
        this.postID = postID;
    }

    public String getAuthorID() {
        return authorID;
    }

    public void setAuthorID(String authorID) {
        this.authorID = authorID;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDateTimeToString() {
        return dateTimeFormatter.format(dateTime);
    }

    public void setDateTimeFromString(String dateTime) {
        this.dateTime = LocalDateTime.parse(dateTime, dateTimeFormatter);
    }
}
