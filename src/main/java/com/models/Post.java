package com.models;

import java.util.ArrayList;
import java.util.UUID;

public class Post {
    private String postID;

    private String content;
    private ArrayList<String> tags;        // also add this post after according tags
    private int likedCount;
    private ArrayList<String> commentIDs;  // fetch comments using commentIDs

    private String anonymousPosterName;    // user can choose a name as an input
    private String userUuid;

    public Post(String content, ArrayList<String> tags, int likedCount, String anonymousPosterName, String userUuid) {
        this.content = content;
        this.tags = tags;
        this.likedCount = likedCount;
        this.commentIDs = new ArrayList<String>();
        this.anonymousPosterName = anonymousPosterName;
        this.userUuid = userUuid;
        postID = UUID.randomUUID().toString();
    }

    public ArrayList<String> getCommentIDs() {
        return commentIDs;
    }

    public void addCommentID(String commentID) {
        commentIDs.add(commentID);
    }

    public ArrayList<String> getTags() {
        return tags;
    }

    public void addTag(String tag) {
        tags.add(tag);
    }

    public void removeTag(String tag) {
        tags.remove(tag);
    }

    public int getLikedCount() {
        return likedCount;
    }

    public void incrementLike() {
        likedCount++;
    }

    public void decrementLike() {
        likedCount--;
    }

    public String getAnonymousPosterName() {
        return anonymousPosterName;
    }

    public String getContent() {
        return content;
    }

    public String getPostID() {
        return postID;
    }

    public String getUserUuid() {
        return userUuid;
    }
}

