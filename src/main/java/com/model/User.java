package com.model;

import java.util.ArrayList;
import java.util.regex.Pattern;

public class User {
    private String uuid;
    private String email;
    private String password;
    private String username;
    private ArrayList<String> postIDs;
    private ArrayList<String> likedPostIDs;

    public User() {
    }

    public User(String email,
                String password,
                String username,
                String uuid,
                ArrayList<String> postIDs,
                ArrayList<String> likedPostIDs) {
        this.email = email;
        this.password = password;
        this.username = username;
        this.uuid = uuid;
        this.postIDs = postIDs;
        this.likedPostIDs = likedPostIDs;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPostIDs(ArrayList<String> postIDs) {
        this.postIDs = postIDs;
    }

    public void setLikedPostIDs(ArrayList<String> likedPostIDs) {
        this.likedPostIDs = likedPostIDs;
    }

    public String getUuid() {
        return uuid;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public ArrayList<String> getPostIDs() {
        return postIDs;
    }

    public ArrayList<String> getLikedPostIDs() {
        return likedPostIDs;
    }

    public static boolean isValidEmailAddress(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";
        Pattern pat = Pattern.compile(emailRegex);
        return pat.matcher(email).matches();
    }

    public static boolean isValidPassword(String psw) {
        return psw.length()>=8;
    }

    @Override
    public String toString() {
        return "User{" +
                "uuid='" + uuid + '\'' +
                ", emailAddress='" + email + '\'' +
                ", password='" + password + '\'' +
                ", userName='" + username + '\'' +
                ", myPostIDs=" + postIDs +
                ", likedPostIDs=" + likedPostIDs +
                '}';
    }
}

