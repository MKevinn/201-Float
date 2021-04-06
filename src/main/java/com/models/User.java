package com.models;

import java.util.ArrayList;
import java.util.regex.Pattern;
import java.util.UUID;

public class User {
    private String uuid;

    private String emailAddress;
    private String password;
    private String userName;
    private ArrayList<String> postIDs;

    public User(String emailAddress, String password, String userName) {
        this.emailAddress = emailAddress;
        this.password = password;
        this.userName = userName;
        this.postIDs = new ArrayList<String>();
        uuid = UUID.randomUUID().toString();
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUuid() {
        return uuid;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public boolean isValidEmailAddress() {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";
        Pattern pat = Pattern.compile(emailRegex);
        return pat.matcher(emailAddress).matches();
    }

    public boolean isValidPassword() {
        return password.length()>=8;
    }

    @Override
    public String toString() {
        return "User{" +
                "uuid='" + uuid + '\'' +
                ", emailAddress='" + emailAddress + '\'' +
                ", password='" + password + '\'' +
                ", userName='" + userName + '\'' +
                ", postIDs=" + postIDs +
                '}';
    }
}

