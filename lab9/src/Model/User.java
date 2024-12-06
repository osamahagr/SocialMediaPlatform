package Model;

import Controller.EmailValidator;
import Controller.PasswordHasher;
import java.util.ArrayList;
import java.util.UUID;

public class User {

    private String userID;
    private String userName;
    private String firstName;
    private String lastName;
    private String email;
    private String hashedPassword;
    private String dateOfBirth;
    private String bio;
    private String profilePhotoPath;
    private String coverPhotoPath;
    private String status; // Online or Offline
    private ArrayList<String> friendsIDs;
    private ArrayList<String> pendingRequests;
    private ArrayList<String> likesIDs;

    public User() {
        this.userID = UUID.randomUUID().toString();
        this.friendsIDs = new ArrayList<>();
        this.likesIDs = new ArrayList<>();
        this.pendingRequests = new ArrayList<>();
        this.dateOfBirth = "";
        this.bio = "";
        this.profilePhotoPath = "";
        this.coverPhotoPath = "";
        this.status = "online";
        this.userName = "";

    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String UserID) {
        this.userID = UserID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUserName() {
        return firstName + lastName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
            this.email = email;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getProfilePhotoPath() {
        return profilePhotoPath;
    }

    public void setProfilePhotoPath(String profilePhotoPath) {
        this.profilePhotoPath = profilePhotoPath;
    }

    public String getCoverPhotoPath() {
        return coverPhotoPath;
    }

    public void setCoverPhotoPath(String coverPhotoPath) {
        this.coverPhotoPath = coverPhotoPath;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ArrayList<String> getFriendsIDs() {
        return friendsIDs;
    }

    public void setFriendsIDs(ArrayList<String> friendsIDs) {
        this.friendsIDs = friendsIDs;
    }

    public ArrayList<String> getPendingRequests() {
        return pendingRequests;
    }

    public void setPendingRequests(ArrayList<String> pendingRequests) {
        this.pendingRequests = pendingRequests;
    }

    public void removeFriend(User f) {
        friendsIDs.remove((String) f.getUserID());
    }

    public boolean isFriend(User u) {
        return friendsIDs.contains(u.getUserID());
    }

    public void addFriend(User f) {
        friendsIDs.add(f.getUserID());
    }

    public boolean liked(Post p) {
        return likesIDs.contains(p.getContentId());
    }

    public void like(Post p) {
        likesIDs.add(p.getContentId());
    }

    public void dislike(Post p) {
        likesIDs.remove((String) p.getContentId());
    }

    public ArrayList<String> getLikesIDs() {
        return likesIDs;
    }

    public void setLikesIDs(ArrayList<String> likesIDs) {
        this.likesIDs = likesIDs;
    }

}
