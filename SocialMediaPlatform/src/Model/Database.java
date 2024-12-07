package Model;

import org.json.JSONArray;
import org.json.JSONObject;
import View.Alert;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class Database {
    private static final String USERS_FILE = "users.json";
    private static final String POSTS_FILE = "posts.json";
    private static final String COMMENTS_FILE = "comments.json";
    private static final String LIKES_FILE = "likes.json";
    private static final String FRIENDS_FILE = "friends.json";
    private static final String FRIEND_REQUESTS_FILE = "friend_requests.json"; 

    public Database() {
        try {
            initializeDatabase();
        } catch (IOException e) {
            new Alert("Error initializing database: " + e.getMessage(), null);
        }
    }

    private void initializeDatabase() throws IOException {
        createFileIfNotExists(USERS_FILE);
        createFileIfNotExists(POSTS_FILE);
        createFileIfNotExists(COMMENTS_FILE);
        createFileIfNotExists(LIKES_FILE);
        createFileIfNotExists(FRIENDS_FILE);
        createFileIfNotExists(FRIEND_REQUESTS_FILE); // Create requests file
    }

    private void createFileIfNotExists(String fileName) throws IOException {
        File file = new File(fileName);
        if (!file.exists()) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                writer.write(new JSONArray().toString());
            }
        }
    }

    // -------------------- User Methods --------------------
    public JSONArray getUsers() {
        return readFromFile(USERS_FILE);
    }

    public Optional<JSONObject> findUserById(String userId) {
        for (Object user : getUsers()) {
            JSONObject userObj = (JSONObject) user;
            if (userObj.getString("userId").equals(userId)) {
                return Optional.of(userObj);
            }
        }
        return Optional.empty();
    }

    public void addUser(JSONObject newUser) {
        if (!newUser.has("userId")) {
            newUser.put("userId", UUID.randomUUID().toString());
        }
        JSONArray users = getUsers();
        users.put(newUser);
        saveToFile(USERS_FILE, users);
    }

    public void updateUser(JSONObject updatedUser) {
        JSONArray users = getUsers();
        for (int i = 0; i < users.length(); i++) {
            JSONObject user = users.getJSONObject(i);
            if (user.getString("userId").equals(updatedUser.getString("userId"))) {
                users.put(i, updatedUser);
                break;
            }
        }
        saveToFile(USERS_FILE, users);
    }
    
    public void updateUserStatus(String userId, String status) {
        JSONArray users = getUsers();
        boolean userFound = false;
 
        for (int i = 0; i < users.length(); i++) {
            JSONObject user = users.getJSONObject(i);

            if (user.getString("userId").equals(userId)) {
                
                user.put("status", status); // Update the status
                userFound = true;
                break;
            }
        }

        if (userFound) {
            saveToFile(USERS_FILE, users);
        } else {
            new Alert("User not found: " + userId, null);
        }
    }


        public void saveUsers(JSONArray users)
    {
         saveToFile(USERS_FILE, users);
    }


    // -------------------- Friend Request Methods --------------------
    public JSONArray getFriendRequests() {
        return readFromFile(FRIEND_REQUESTS_FILE);
    }
       public void saveFriends(JSONArray friends) {
        saveToFile(FRIENDS_FILE, friends);
    }


    public void sendFriendRequest(String senderId, String receiverId) {
        JSONObject request = new JSONObject();
        request.put("senderId", senderId);
        request.put("receiverId", receiverId);
        request.put("status", "pending"); 

        JSONArray requests = getFriendRequests();
        requests.put(request);
        saveToFile(FRIEND_REQUESTS_FILE, requests);
    }

    public void acceptFriendRequest(String senderId, String receiverId) {
        JSONArray requests = getFriendRequests();
        for (int i = 0; i < requests.length(); i++) {
            JSONObject request = requests.getJSONObject(i);
            if (request.getString("senderId").equals(senderId) && request.getString("receiverId").equals(receiverId) && request.getString("status").equals("pending")) {
                request.put("status", "accepted");
                break;
            }
        }
        saveToFile(FRIEND_REQUESTS_FILE, requests);
        addFriend(senderId, receiverId); // Add the users as friends
    }

    public void declineFriendRequest(String senderId, String receiverId) {
        JSONArray requests = getFriendRequests();
        for (int i = 0; i < requests.length(); i++) {
            JSONObject request = requests.getJSONObject(i);
            if (request.getString("senderId").equals(senderId) && request.getString("receiverId").equals(receiverId) && request.getString("status").equals("pending")) {
                request.put("status", "declined");
                break;
            }
        }
        saveToFile(FRIEND_REQUESTS_FILE, requests);
    }

    // -------------------- Friend Methods --------------------
   public JSONArray getFriends(String userId) {
        return readFromFile(FRIENDS_FILE);
    }
    
public void addFriend(String userId, String friendId) {
    JSONArray friends = getFriends(userId);
    JSONObject friendship = new JSONObject();
    friendship.put("userId", userId);
    friendship.put("friendId", friendId);

    // Avoid duplicates in friends.json
    for (Object obj : friends) {
        JSONObject friendPair = (JSONObject) obj;
        if (friendPair.getString("userId").equals(userId) &&
            friendPair.getString("friendId").equals(friendId)) {
            return; // Friendship already exists
        }
    }

    friends.put(friendship);
    saveToFile(FRIENDS_FILE, friends);
}


    public void removeFriend(String userId, String friendId) {
        JSONArray friends = getFriends(userId);
        for (int i = 0; i < friends.length(); i++) {
            JSONObject friend = friends.getJSONObject(i);
            if (friend.getString("userId").equals(userId) && friend.getString("friendId").equals(friendId)) {
                friends.remove(i);
                break;
            }
        }
        saveToFile(FRIENDS_FILE, friends);
    }

   public List<String> getFriendsIDs(String userId) {
    List<String> friendsIDs = new ArrayList<>();
    JSONArray friends = getFriends(userId);
    for (Object friendObj : friends) {
        JSONObject friend = (JSONObject) friendObj;
        if (friend.getString("userId").equals(userId)) {
            friendsIDs.add(friend.getString("friendId"));
        } else if (friend.getString("friendId").equals(userId)) {
            friendsIDs.add(friend.getString("userId"));
        }
    }
    return friendsIDs;
}

    public List<String> getUserLikedPosts(String userId) {
    List<String> likedPostIds = new ArrayList<>();
    JSONArray likes = getLikes();  // Assuming this method returns a JSONArray of likes
    
    for (Object likeObj : likes) {
        JSONObject like = (JSONObject) likeObj;
        
        if (like.getString("userId").equals(userId)) {
            likedPostIds.add(like.getString("postId"));
        }
    }
    return likedPostIds;  // Return the list of liked post IDs as Strings
}

    // -------------------- Post Methods --------------------
    public JSONArray getPosts() {
        return readFromFile(POSTS_FILE);
    }

    public Optional<JSONObject> findPostById(String postId) {
        for (Object post : getPosts()) {
            JSONObject postObj = (JSONObject) post;
            if (postObj.getString("contentId").equals(postId)) {
                return Optional.of(postObj);
            }
        }
        return Optional.empty();
    }

    public void addPost(JSONObject newPost) {
        if (!newPost.has("postId")) {
            newPost.put("postId", UUID.randomUUID().toString());
        }
        JSONArray posts = getPosts();
        posts.put(newPost);
        saveToFile(POSTS_FILE, posts);
    }

    public void savePosts(JSONArray posts) {
        saveToFile(POSTS_FILE, posts);
    }

//    public void removeExpiredStories() {
//        JSONArray allPosts = getPosts();
//        JSONArray updatedPosts = new JSONArray();
//
//        for (Object obj : allPosts) {
//            JSONObject postJson = (JSONObject) obj;
//            Post post = new Post(
//                postJson.getString("authorId"),
//                postJson.getString("content"),
//                postJson.getString("imagePath"),
//                postJson.getString("type")
//            );
//
//            // Only keep posts that are not expired stories
//            if (!(post.getType().equals("story") && post.isStoryExpired())) {
//                updatedPosts.put(postJson);
//            }
//        }
//
//        // Save the updated posts (remove expired stories)
//        savePosts(updatedPosts);
//    }
    // -------------------- Comment Methods --------------------
    public JSONArray getComments() {
        return readFromFile(COMMENTS_FILE);
    }

    public Optional<JSONObject> findCommentById(String commentId) {
        for (Object comment : getComments()) {
            JSONObject commentObj = (JSONObject) comment;
            if (commentObj.getString("commentId").equals(commentId)) {
                return Optional.of(commentObj);
            }
        }
        return Optional.empty();
    }

    public void addComment(JSONObject newComment) {
        if (!newComment.has("commentId")) {
            newComment.put("commentId", UUID.randomUUID().toString());
        }
        JSONArray comments = getComments();
        comments.put(newComment);
        saveToFile(COMMENTS_FILE, comments);
    }

    public void saveComments(JSONArray comments) {
        saveToFile(COMMENTS_FILE, comments);
    }

    // -------------------- Like Methods --------------------
    public JSONArray getLikes() {
        return readFromFile(LIKES_FILE);
    }

    public void saveLikes(JSONArray likes) {
        saveToFile(LIKES_FILE, likes);
    }

    public void addLike(JSONObject like) {
        JSONArray likes = getLikes();
        likes.put(like);
        saveToFile(LIKES_FILE, likes);
    }

    public void removeLike(JSONObject like) {
        JSONArray likes = getLikes();
        for (int i = 0; i < likes.length(); i++) {
            JSONObject obj = likes.getJSONObject(i);
            if (obj.getString("userId").equals(like.getString("userId"))
                && obj.getString("postId").equals(like.getString("postId"))) {
                likes.remove(i);
                break;
            }
        }
        saveToFile(LIKES_FILE, likes);
    }


    // -------------------- Utility Methods --------------------
    private JSONArray readFromFile(String fileName) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            StringBuilder jsonContent = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                jsonContent.append(line);
            }
            return new JSONArray(jsonContent.toString());
        } catch (IOException e) {
            new Alert("Error reading from file " + fileName + ": " + e.getMessage(), null);
            return new JSONArray();
        }
    }

    private void saveToFile(String fileName, JSONArray jsonArray) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write(jsonArray.toString(5));
        } catch (IOException e) {
            new Alert("Error saving to file " + fileName + ": " + e.getMessage(), null);
        }
    }
}
