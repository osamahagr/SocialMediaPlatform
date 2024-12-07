package View;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import Controller.CreateUser;
import Controller.EmailValidator;
import Controller.PasswordHasher;
import Model.Database;
import Model.User;

public class Welcome {

    public Welcome(Database database) {
        JFrame frame = new JFrame();
                frame.setResizable(false);

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(null);
        panel.setBorder(BorderFactory.createEmptyBorder(53, 84, 76, 84));
        panel.add(new JLabel("Welcome", 40, GUIConstants.blue, Font.BOLD), BorderLayout.NORTH);

        JPanel center = new JPanel(new GridLayout(6, 1, 10, 10));
        center.setBackground(null);
        center.setBorder(BorderFactory.createEmptyBorder(22, 231, 17, 231));
        JTextField firstName = new JTextField("First Name");
        center.add(firstName);
        JTextField lastName = new JTextField("Last Name");
        center.add(lastName);
        JTextField email = new JTextField("Email");
        center.add(email);
        JTextField password = new JTextField("Password");
        center.add(password);
        JTextField confirmPassword = new JTextField("Confirm Password");
        center.add(confirmPassword);
        JButton createAcc = new JButton("Create Account", 45, 20);

        createAcc.addMouseListener(new MouseListener() {
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
                if (firstName.isEmpty()) {
                    new Alert("First Name cannot be empty", frame);
                    return;
                }
                if (lastName.isEmpty()) {
                    new Alert("Last Name cannot be empty", frame);
                    return;
                }
                if (email.isEmpty()) {
                    new Alert("Email cannot be empty", frame);
                    return;
                }
                if (password.isEmpty()) {
                    new Alert("Password cannot be empty", frame);
                    return;
                }
                if (password.getText().length() < 6) {
                    new Alert("Password must contains at least 6 characters", frame);
                    return;
                }
                if (confirmPassword.isEmpty()) {
                    new Alert("Please confirm password", frame);
                    return;
                }
                if (!password.getText().equals(confirmPassword.getText())) {
                    new Alert("Password doesn't match", frame);
                    return;
                }
     try {
            // Create a temporary user object for validation
            User tempUser = new User();
            tempUser.setEmail(email.getText().trim());

            CreateUser createUser = new CreateUser(tempUser, database);

            // Check if email is already used
            if (createUser.isEmailUsed()) {
                new Alert("This email has been used before", frame);
                return;
            }

             //Proceed with creating the user
            User user = new User();
            user.setFirstName(firstName.getText().trim());
            user.setLastName(lastName.getText().trim());
             if (!EmailValidator.isValidEmail(email.getText().trim())) {
            new Alert("Invalid email format", frame);
            return;
        }
            user.setEmail(email.getText().trim());
            user.setHashedPassword(PasswordHasher.hashPassword(password.getText().trim()));

            createUser = new CreateUser(user, database);
            createUser.create();

            // Verify if the user was successfully created
            User createdUser = createUser.getUser(email.getText().trim(), user.getHashedPassword());
            if (createdUser != null) {
                new Home(createdUser, database);
                frame.dispose();
            } else {
                new Alert("Failed to create user. Please try again.", frame);
            }
        } catch (Exception ex) {
            new Alert("Error creating account: " + ex.getMessage(), frame);
        }}
});

        center.add(createAcc);

        panel.add(center, BorderLayout.CENTER);

JLabel login = new JLabel("Already have an account? Login", 20,
				GUIConstants.black, Font.BOLD);        login.addMouseListener(new MouseListener() {
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
                new Login(database);
                frame.dispose();
            }
        });
        login.setCursor(new Cursor(Cursor.HAND_CURSOR));
        login.setHorizontalAlignment(JLabel.CENTER);
        panel.add(login, BorderLayout.SOUTH);

        frame.getContentPane().add(panel);

        frame.setVisible(true);
        frame.requestFocus();
    }


 
}
 