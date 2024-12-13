package View;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;



import Controller.CreatePost;
import Controller.GenerateTimeline;
import Model.Database;
import Model.User;
import java.util.List;
import org.json.JSONObject;
import Controller.SearchGroups;
import Controller.SearchUsers;

public class Home {

    public Home(User user, Database database) {
        JFrame frame = new JFrame();
        frame.getContentPane().setLayout(new BorderLayout());
        frame.setResizable(false);

        JPanel sideBar = new JPanel();
        sideBar.setBackground(GUIConstants.white);
        Dimension sideBarDim = new Dimension(182, 1000);
        sideBar.setPreferredSize(sideBarDim);
        sideBar.setMaximumSize(sideBarDim);
        sideBar.setMinimumSize(sideBarDim);
        sideBar.setLayout(new BoxLayout(sideBar, BoxLayout.Y_AXIS));
        sideBar.add(Box.createVerticalStrut(10));

        // Profile Section
        JPanel profile = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        profile.setMaximumSize(new Dimension(182, 50));
        profile.setBackground(GUIConstants.white);
        profile.setCursor(new Cursor(Cursor.HAND_CURSOR));
        profile.add(new JLabel(user.getUserName(), 19, GUIConstants.black, Font.BOLD));

// Add MouseListener to open the Profile management page
        profile.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new Profile(user, database); // Open Profile Management
                frame.dispose();            // Close the current home window
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }
        });

        sideBar.add(profile);

        sideBar.add(profile);
        sideBar.add(Box.createVerticalStrut(3));
        sideBar.add(new SideButton("Posts", "myposts", user, database, frame));
        sideBar.add(Box.createVerticalStrut(3));
        sideBar.add(new SideButton("Comments", "mycomments", user, database, frame));
        sideBar.add(Box.createVerticalStrut(3));
        sideBar.add(new SideButton("Likes", "mylikes", user, database, frame));
        sideBar.add(Box.createVerticalStrut(3));
        sideBar.add(new SideButton("Friends", "friends", user, database, frame));
        sideBar.add(Box.createVerticalStrut(3));

        // Add Logout Button at the Bottom
        JPanel logoutPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        logoutPanel.setBackground(GUIConstants.white);
        logoutPanel.setMaximumSize(new Dimension(182, 50));

        JButton logoutButton = new JButton("Logout", 30, 20);
        logoutButton.setPreferredSize(new Dimension(100, 40));
        logoutButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        logoutButton.addMouseListener(new MouseListener() {
            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                // Update user status to offline
                user.setStatus("offline");
                database.updateUserStatus(user.getUserID(), user.getStatus());

                // Redirect to login screen
                new Login(database);
                frame.dispose();
            }
        });
        logoutPanel.add(logoutButton);
        sideBar.add(Box.createVerticalGlue());
        sideBar.add(logoutPanel);

        frame.getContentPane().add(sideBar, BorderLayout.WEST);
         
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchPanel.setMaximumSize(new Dimension(600, 40));
        searchPanel.setBackground(GUIConstants.background);

        JTextField searchField = new JTextField("Search for users or groups.");
        searchField.setPreferredSize(new Dimension(300, 30));
        searchField.setMaximumSize(new Dimension(300, 30));
        searchField.setMinimumSize(new Dimension(300, 30));

        JButton searchButton = new JButton("Search", 200, 20);
        searchButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        searchButton.addMouseListener(new MouseListener() {
            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                String query = searchField.getText().trim();
                if (!query.isEmpty()) {

                    SearchUsers searchUsers = new SearchUsers(database);
                    List<JSONObject> users = searchUsers.searchUsers(query);
                    SearchGroups searchGroups = new SearchGroups(database);
                    List<JSONObject> groups = searchGroups.searchGroups(query);

                    // Display search results (for simplicity, replacing the searchPanel content)
                    searchPanel.removeAll();

                    // Check if any users were found
                    if (!users.isEmpty()) {
                        searchPanel.add(new JLabel("Users Found:", 20, GUIConstants.black, Font.BOLD));

                        for (Object foundUser : users) {
                            if (foundUser instanceof JSONObject) {
                                JSONObject jsonUser = (JSONObject) foundUser;

                                // Debugging output to inspect the JSON structure
                                System.out.println(jsonUser.toString());  // Inspect the contents of jsonUser

                                // Safely get the userName
                                String userName = jsonUser.optString("username", "Unknown User");  // Default to "Unknown User" if key is missing

                                // Ensure the username is not "Unknown User" and print for debugging
                                if ("Unknown User".equals(userName)) {
                                    System.out.println("username is missing or invalid.");
                                }

                                // Create a User object from the JSONObject data
                              // Set the username

                                // Add the User's username to the search panel
                                searchPanel.add(new JLabel(userName, 20, GUIConstants.black, Font.BOLD));
                            }
                        }
                    } else {
                        searchPanel.add(new JLabel("No users found.", 20, GUIConstants.black, Font.BOLD));
                    }

                    // Check if any groups were found
                    if (!groups.isEmpty()) {
                        searchPanel.add(new JLabel("Groups Found:", 20, GUIConstants.black, Font.BOLD));
                        for (JSONObject foundGroup : groups) {
                            // Process group data and display group name
                            // For example, assuming the group name is stored in a key "groupName"
                            String groupName = foundGroup.optString("name", "Unknown Group");
                            searchPanel.add(new JLabel(groupName, 20, GUIConstants.black, Font.BOLD));
                        }
                    } else {
                        searchPanel.add(new JLabel("No groups found.", 20, GUIConstants.black, Font.BOLD));
                    }
                    // If no users or groups are found, show a "No results found" message
                    if (users.isEmpty() && groups.isEmpty()) {
                        searchPanel.add(new JLabel("No results found.", 20, GUIConstants.black, Font.BOLD));
                    }

                    // Revalidate and repaint the panel to reflect the changes
                    searchPanel.revalidate();
                    searchPanel.repaint();
                }
            }
        });
        
        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        
        panel.setBackground(null); 
        panel.add(searchPanel);
        
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(GUIConstants.white);
        Dimension dimension = new Dimension(500, 159);
        header.setPreferredSize(dimension);
        header.setMinimumSize(dimension);
        header.setMaximumSize(dimension);
        header.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

        JPanel north = new JPanel(new BorderLayout());
        north.setBackground(null);
        north.add(new JLabel("Home", 20, GUIConstants.black, Font.BOLD),
                BorderLayout.WEST);
        header.add(north, BorderLayout.NORTH);

        JTextArea postIn = new JTextArea("Share your thoughts...", 18, 20);
        header.add(new JScrollPane(postIn), BorderLayout.CENTER);

        JPanel south = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        south.setBackground(null);

        JButton postBtn = new JButton("Post", 35, 16);
        postBtn.addMouseListener(new MouseListener() {
            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                if (postIn.isEmpty()) {
                    new Alert("Cannot publish empty post", frame);
                    return;
                }
                Model.Post post = new Model.Post(user, postIn.getText(), null);
                if (new CreatePost(post, database).addPost()) {
                    new Alert("Posted successfully", frame);
                    postIn.setText("");
                }
            }
        });
        postBtn.setPreferredSize(new Dimension(81, 37));
        south.add(postBtn);
        header.add(south, BorderLayout.SOUTH);

        panel.add(header);

        ArrayList<Model.Post> posts = new GenerateTimeline(user, database).getPosts();
        for (int i = 0; i < posts.size(); i++) {
            panel.add(Box.createVerticalStrut(7));
            panel.add(new Post(user, posts.get(i), database, frame));
        }


        frame.getContentPane().add(new JScrollPane(panel), BorderLayout.CENTER);
        frame.getContentPane().add(Box.createHorizontalStrut(182), BorderLayout.EAST);

        frame.setVisible(true);
        frame.requestFocus();
    }
}
