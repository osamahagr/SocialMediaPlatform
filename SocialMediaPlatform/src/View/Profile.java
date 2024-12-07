package View;

import Controller.UpdateUser;
import Model.Database;
import Model.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import javax.swing.border.EmptyBorder;

public class Profile {

    public Profile(User user, Database database) {
        // Frame Setup
        JFrame frame = new JFrame();
        frame.setLayout(new BorderLayout());
        frame.setSize(900, 800);  // Adjusted frame size for better spacing
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Main panel with padding
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(new EmptyBorder(20, 30, 20, 30));
        frame.add(new JScrollPane(mainPanel), BorderLayout.CENTER);

        // Title Label (Centered)
        JLabel titleLabel = new JLabel("Edit Profile", 24, GUIConstants.black, Font.BOLD);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(titleLabel);
        mainPanel.add(Box.createVerticalStrut(20));

        // Cover Photo Section (Wide)
        addCoverPhotoSection(mainPanel, user, database);
        mainPanel.add(Box.createVerticalStrut(20));

        // Profile Photo Section (Circular)
        addProfilePhotoSection(mainPanel, user, database);
        mainPanel.add(Box.createVerticalStrut(20));

        // Bio Section
        addTextAreaSection(mainPanel, user, database);
        mainPanel.add(Box.createVerticalStrut(20));

        // Password Section
        addPasswordSection(mainPanel, user, database);

        frame.setVisible(true);
    }

    // Method to add Cover Photo Section
    private void addCoverPhotoSection(JPanel mainPanel, User user, Database database) {
        // Upload Cover Photo Button
        JLabel coverPhotoLabel = new JLabel("Cover Photo:", 16, GUIConstants.black, Font.PLAIN);
        coverPhotoLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JButton uploadCoverButton = new JButton("Upload Cover Photo",30,15);
        uploadCoverButton.setBackground(GUIConstants.blue);
        uploadCoverButton.setForeground(GUIConstants.white);
        uploadCoverButton.setBorder(BorderFactory.createLineBorder(GUIConstants.blue));
        uploadCoverButton.setOpaque(true);
        uploadCoverButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        uploadCoverButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int result = fileChooser.showOpenDialog(null);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    user.setCoverPhotoPath(selectedFile.getAbsolutePath());
                    new UpdateUser(user, database).update();
                    new Alert("Cover photo updated successfully!", null);
                }
            }
        });

        JPanel coverPanel = new JPanel();
        coverPanel.setLayout(new BoxLayout(coverPanel, BoxLayout.Y_AXIS));
        coverPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        coverPanel.add(coverPhotoLabel);
        coverPanel.add(uploadCoverButton);

        mainPanel.add(coverPanel);
    }

    // Method to add Profile Photo Section (Circular photo)
    private void addProfilePhotoSection(JPanel mainPanel, User user, Database database) {
        // Profile Photo Upload Button
        JLabel profilePhotoLabel = new JLabel("Profile Photo:", 16, GUIConstants.black, Font.PLAIN);
        profilePhotoLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JButton uploadProfileButton = new JButton("Upload Profile Photo",30,15);
        uploadProfileButton.setBackground(GUIConstants.blue);
        uploadProfileButton.setForeground(GUIConstants.white);
        uploadProfileButton.setBorder(BorderFactory.createLineBorder(GUIConstants.blue));
        uploadProfileButton.setOpaque(true);
        uploadProfileButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        uploadProfileButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int result = fileChooser.showOpenDialog(null);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    user.setProfilePhotoPath(selectedFile.getAbsolutePath());
                    new UpdateUser(user, database).update();
                    new Alert("Profile photo updated successfully!", null);
                }
            }
        });

        JPanel profilePanel = new JPanel();
        profilePanel.setLayout(new BoxLayout(profilePanel, BoxLayout.Y_AXIS));
        profilePanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        profilePanel.add(profilePhotoLabel);
        profilePanel.add(uploadProfileButton);

        mainPanel.add(profilePanel);
    }

    // Method to add Bio Section
    private void addTextAreaSection(JPanel mainPanel, User user, Database database) {
        JPanel bioPanel = new JPanel();
        bioPanel.setLayout(new BoxLayout(bioPanel, BoxLayout.Y_AXIS));
        bioPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel bioLabel = new JLabel("Edit Bio:", 16, GUIConstants.black, Font.PLAIN);
        bioLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        bioPanel.add(bioLabel);

        JTextArea bioTextArea = new JTextArea(user.getBio(), 5, 20);
        bioTextArea.setLineWrap(true);
        bioTextArea.setWrapStyleWord(true);
        bioTextArea.setFont(new Font("Arial", Font.PLAIN, 14));
        bioTextArea.setAlignmentX(Component.LEFT_ALIGNMENT);
        bioTextArea.setBorder(BorderFactory.createLineBorder(GUIConstants.blue));

        // Save Bio Button
        JButton saveBioButton = new JButton("Save Bio",30,15);
        saveBioButton.setBackground(GUIConstants.blue);
        saveBioButton.setForeground(GUIConstants.white);
        saveBioButton.setBorder(BorderFactory.createLineBorder(GUIConstants.blue));
        saveBioButton.setOpaque(true);
        saveBioButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                user.setBio(bioTextArea.getText());
                new UpdateUser(user, database).update(); // Save the updated bio
                new Alert("Bio updated successfully!", null);
            }
        });

        bioPanel.add(new JScrollPane(bioTextArea));
        bioPanel.add(Box.createVerticalStrut(10));
        bioPanel.add(saveBioButton);

        mainPanel.add(bioPanel);
    }

    // Method to add Password Section
    private void addPasswordSection(JPanel mainPanel, User user, Database database) {
        JPanel passwordPanel = new JPanel();
        passwordPanel.setLayout(new BoxLayout(passwordPanel, BoxLayout.Y_AXIS));
        passwordPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel passwordLabel = new JLabel("Change Password:", 16, GUIConstants.black, Font.PLAIN);
        passwordLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JButton changePasswordButton = new JButton("Change Password",30,15);
        changePasswordButton.setBackground(GUIConstants.blue);
        changePasswordButton.setForeground(GUIConstants.white);
        changePasswordButton.setBorder(BorderFactory.createLineBorder(GUIConstants.blue));
        changePasswordButton.setOpaque(true);

        changePasswordButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new ChangePassword(user, database); // Open the ChangePassword dialog
            }
        });

        passwordPanel.add(passwordLabel);
        passwordPanel.add(Box.createVerticalStrut(10));
        passwordPanel.add(changePasswordButton);
        passwordPanel.add(Box.createVerticalStrut(20));

        mainPanel.add(passwordPanel);
    }
}
