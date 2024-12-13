package Controller;

import java.sql.ResultSet;
import java.sql.SQLException;

import Model.Database;
import Model.User;
import View.Alert;


public class ReadUserByID {
    private User user;

    public ReadUserByID(String userId, Database database) {
        try {
            database.findUserById(userId).ifPresent(userJson -> {
                user = new User();
                user.setUserID(userJson.getString("userId"));
                user.setFirstName(userJson.getString("firstName"));
                user.setLastName(userJson.getString("lastName"));
                user.setEmail(userJson.getString("email"));
            });
        } catch (Exception e) {
            new Alert("Error reading user by ID: " + e.getMessage(), null);
        }
    }

    public User getUser() {
        return user;
    }
}

