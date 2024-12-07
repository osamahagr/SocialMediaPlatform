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

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(null);

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
