package com.utilities;

import com.models.*;

import java.util.ArrayList;

public class DatabaseManager {

    public static DatabaseManager shared = new DatabaseManager();

    private User getSampleUser() {
        return new User("email@gmail.com","qwerty123456","usr");
    }

    private Post getSamplePost() {
        return new Post("This is some good content...",
                new ArrayList<String>(),
                0,
                "randomName",
                "uuid-uuid-123456");
    }

    private ArrayList<Post> getSamplePosts() {
        ArrayList<Post> arr = new ArrayList<Post>();
        for (int i=0; i<10; i++) {
            arr.add(getSamplePost());
        }
        return arr;
    }

    private DatabaseManager() {
        System.out.println("Establishing connection to db...");
        // make connections to db
    }

    public void insertUserToDb(User user) {
        System.out.println(String.format("Inserting user to db...",user.toString()));
        // insert user to db
    }

    public User auth(String email, String psw) {
        // authentication
        // find user
        return getSampleUser();
    }

    public void insertPostToDbAfter(String userID, Post post) {
        // insert a new post
    }

    public Post queryPostBy(String postID) {
        // get a post
        return getSamplePost();
    }

    public ArrayList<Post> queryPostsBy(String keyword) {
        // get posts
        return getSamplePosts();
    }

}
