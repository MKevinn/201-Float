package com.model;

public class Comment {
    private String commentID;
    private String anonymousPosterName;
    private String content;

    public Comment(String commentID, String anonymousPosterName, String content) {
        this.commentID = commentID;
        this.anonymousPosterName = anonymousPosterName;
        this.content = content;
    }

    public Comment() {
    }

    public String getCommentID() {
        return commentID;
    }

    public String getAnonymousPosterName() {
        return anonymousPosterName;
    }

    public String getContent() {
        return content;
    }
}
