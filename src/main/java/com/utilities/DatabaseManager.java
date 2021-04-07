package com.utilities;

import com.models.*;

public class DatabaseManager {

    public static DatabaseManager shared = new DatabaseManager();

    private DatabaseManager() {
        System.out.println("Establishing connection to db...");
        // make connections to db
    }

    public void insertUserToDatabase(User user) {
        System.out.println(String.format("Inserting user to db...",user.toString()));
        // insert user to db
    }

    public User auth(String email, String psw) {
        // authentication
        // find user
        User user = new User(email,psw,"usr");
        return user;
    }

}
