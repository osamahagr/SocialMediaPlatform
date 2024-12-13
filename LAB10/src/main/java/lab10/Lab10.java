
package LAB10;

import Controller.GroupController;
import Model.Group;
import Model.Database;
import View.Welcome;
import org.json.JSONObject;

public class LAB10 {




    public static void main(String[] args) {
      
        // new Welcome(new Database());
        
        Database mockDatabase = new Database();

        GroupController groupController = new GroupController(mockDatabase);

        String primaryAdminId = "51fe9871-9665-496c-8963-03d300f3418e";
        String adminId = "f9779367-1aaa-4633-be84-52309240ece7";        
        String normalUserId = "ab8d9f3c-84d3-4e04-b105-1711388d942f";    

       Group g =  groupController.createGroup(primaryAdminId, "My Group", "A group for testing", "");
        System.out.println("Group created!");
        String GID = g.getGroupId();
        
        groupController.addMemberToGroup(GID,adminId , "Normal User", primaryAdminId);
        groupController.addMemberToGroup(GID,normalUserId, "Admin", primaryAdminId);
        groupController.addMemberToGroup(GID,normalUserId, "Admin", primaryAdminId);


        groupController.addPostToGroup(GID, primaryAdminId, "This is an important announcement!");
        System.out.println("Post added by Admin!");

        groupController.editPostInGroup(GID, primaryAdminId, 0, "Updated announcement!");
        System.out.println("Post edited by Admin!");

        groupController.approveMembership(GID, normalUserId, primaryAdminId);
        System.out.println("Normal user approved as member!");

        groupController.addPostToGroup(GID, normalUserId, "Normal user post.");
        System.out.println("Post added by Normal User!");

        groupController.editPostInGroup(GID, normalUserId, 1, "Trying to edit post");
        System.out.println("Attempt to edit a post by Normal User");

        groupController.removeMember(GID, normalUserId, adminId);
        System.out.println("Normal user removed from group by Admin");

        groupController.deletePostFromGroup(GID, adminId, 0);
        System.out.println("Post deleted by Admin");

        groupController.leaveGroup(GID, normalUserId);
        System.out.println("Normal user leaves the group!");

       // groupController.deleteGroup(GID);
        System.out.println("Group deleted!");
    }
}
