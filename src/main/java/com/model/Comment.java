package com.model;

public class Comment {
    private String anonymousPosterName;
    private String content;

    public Comment(String anonymousPosterName, String content) {
        this.anonymousPosterName = anonymousPosterName;
        this.content = content;
    }

    public Comment() {
    }

    public String getAnonymousPosterName() {
        return anonymousPosterName;
    }

    public String getContent() {
        return content;
    }
}
