/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;



import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

/**
 *
 * @author PC
 */
class Story {
     private String contentId;
        private String authorId;
        private String content;
        private User user;
        private String imagePath; 
        private LocalDateTime dateTime;

        private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

      public Story() {
        this.dateTime = LocalDateTime.now();
    }

        public Story(User user, String content, String imagePath) {
            this.contentId = UUID.randomUUID().toString();  // Unique identifier
            this.authorId = user.getUserID();
            this.content = content;
            this.imagePath = imagePath;
            this.dateTime = LocalDateTime.now();
        }

    public void setContentId(String contentId) {
        this.contentId = contentId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getContentId() {
        return contentId;
    }

    public String getAuthorId() {
        return authorId;
    }

    public String getContent() {
        return content;
    }

    public User getUser() {
        return user;
    }

    public String getImagePath() {
        return imagePath;
    }

   public boolean isStoryExpired() {
            return dateTime.plusHours(24).isBefore(LocalDateTime.now());
      }
}
