
    package Model;

    import java.time.LocalDateTime;
    import java.time.format.DateTimeFormatter;
    import java.util.UUID;

    public class Post {

        private String contentId;
        private String authorId;
        private String content;
        private User user;
        private String imagePath; 
        private LocalDateTime dateTime;

        private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        public Post() {
        this.dateTime = LocalDateTime.now();
    }

        public Post(User user, String content, String imagePath) {
            this.contentId = UUID.randomUUID().toString();  // Unique identifier
            this.authorId = user.getUserID();
            this.content = content;
            this.imagePath = imagePath;
            this.dateTime = LocalDateTime.now();
        }

        // Getters and Setters
        public String getContentId() {
            return contentId;
        }

        public String getAuthorId() {
            return authorId;
        }

    public User getUser() {
        return user;
    }

        public String getContent() {
            return content;
        }

        public String getImagePath() {
            return imagePath;
        }

        public LocalDateTime getTimestamp() {
            return dateTime;
        }


//        public boolean isStoryExpired() {
//            // A story expires 24 hours after creation
//            return type.equals("story") && timestamp.plusHours(24).isBefore(LocalDateTime.now());
//        }

        public String getTimestampAsString() {
            return dateTimeFormatter.format(dateTime);
        }

        public void setContentId(String contentId) {
            this.contentId = contentId;
        }
        public void setUser(User user)
        {
            this.user = user;
        }

        public void setAuthorId(String authorId) {
            this.authorId = authorId;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public void setImagePath(String imagePath) {
            this.imagePath = imagePath;
        }

       
         public void setDateTimeFromString(String dateTime) {
        if (dateTime != null && !dateTime.isEmpty()) {
            this.dateTime = LocalDateTime.parse(dateTime, dateTimeFormatter);
        } else {
            this.dateTime = LocalDateTime.now(); 
        }
    }

    public String getDateToString() {
        if (dateTime != null) {
            return dateTimeFormatter.format(dateTime);
        }
        return "Invalid Date"; 
    }

    }