package com.model;

public class Notification {
    private String notificationID;
    private String postID;
    private String userUuidToNotify;
    private String anonymousPosterName;         // same property in comment
    private String content;                 // same property in comment

    public String getNotificationID() {
        return notificationID;
    }

    public String getPostID() {
        return postID;
    }

    public String getUserUuidToNotify() {
        return userUuidToNotify;
    }

    public String getAnonymousPosterName() {
        return anonymousPosterName;
    }

    public String getContent() {
        return content;
    }

    public Notification() {
    }

    public Notification(String notificationID, String postID, String userUuidToNotify, String anonymousPosterName, String content) {
        this.notificationID = notificationID;
        this.postID = postID;
        this.userUuidToNotify = userUuidToNotify;
        this.anonymousPosterName = anonymousPosterName;
        this.content = content;
    }
}
