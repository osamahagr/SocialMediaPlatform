/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;


import java.util.regex.Pattern;

public class EmailValidator {

    // Pre-compiled regex pattern for better performance
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
        "^[\\w._%+-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$"
    );

    public static boolean isValidEmail(String email) {
        if (email == null || email.isEmpty()) {
            return false;
        }
        return EMAIL_PATTERN.matcher(email).matches();
    }
}
