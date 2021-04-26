package com.model;

import java.util.ArrayList;
import java.util.Date;

public class Post {
    private String postID;

    private String content;
    private ArrayList<String> tags;
    private int likedCount;
    private ArrayList<Comment> comments;  // after get a post json data, fetch comments using commentIDs

    private String anonymousPosterName;
    private String userUuid;

    private Date timestamp;

    public Post() {
    }

    public Post(String postID,
                String content,
                ArrayList<String> tags,
                int likedCount,
                ArrayList<Comment> comments,
                String anonymousPosterName,
                String userUuid,
                Date timestamp) {
        this.postID = postID;
        this.content = content;
        this.tags = tags;
        this.likedCount = likedCount;
        this.comments = comments;
        this.anonymousPosterName = anonymousPosterName;
        this.userUuid = userUuid;
        this.timestamp = timestamp;
    }

    public ArrayList<Comment> getComments() {
        return comments;
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

    public Date getTimestamp() {
        return timestamp;
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

	public void setComments(ArrayList<Comment> postCommentData) {
		comments = postCommentData;
	}
}

