package timetable.Authentication;

import timetable.FileHandler;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class AuthenticationService {
    private List<User> userList;
    private String authFileName;

    public AuthenticationService(String authFileName) {
        this.authFileName = authFileName;
        this.userList = FileHandler.loadFromFile(authFileName);
        if (userList == null) {
            this.userList = new ArrayList<>();
        }
        System.out.println("Authentication data  is loaded. Current users: " + userList.size());
    }

    // register as a new user
    public boolean register(String username, String password) {
        if (userExists(username)) {
            System.out.println("Error: Username already exists.");
            return false;
        }
        String hashedPassword = hashPassword(password);
        User newUser = new User(username, hashedPassword);
        userList.add(newUser);
        FileHandler.saveToFile(userList, authFileName);
        System.out.println("student registered successfully: " + username);
        return true;
    }

    //login user
    public boolean login(String username, String password) {
        for (User user : userList) {
            if (user.getUsername().equalsIgnoreCase(username) &&
                    user.getHashedPassword().equals(hashPassword(password))) {
                System.out.println("Login successful. Welcome, " + username + "!");
                return true;
            }
        }
        System.out.println("Error: Invalid username or password.");
        return false;
    }

    //checks if user exists
    private boolean userExists(String username) {
        return userList.stream().anyMatch(user -> user.getUsername().equalsIgnoreCase(username));
    }
    // New method to display registered users
    public void displayRegisteredUsers() {
        if (userList.isEmpty()) {
            System.out.println("No registered users found.");
            return;
        }

        System.out.println("\nRegistered Users:");
        for (User user : userList) {
            System.out.println("- " + user.getUsername());
        }
    }

    private String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = md.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hashedBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error: Unable to hash password", e);
        }
    }
}
