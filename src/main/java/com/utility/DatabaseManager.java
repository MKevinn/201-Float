package com.utility;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import com.model.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

import com.google.auth.oauth2.GoogleCredentials;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.lang.Nullable;

public class DatabaseManager {

    public static DatabaseManager shared = new DatabaseManager();

    private Firestore db;

    private Post getSamplePost() {
        return new Post(UUID.randomUUID().toString(),
                "content......",
                new ArrayList<>(),
                5,
                new ArrayList<>(),
                "the-name",
                UUID.randomUUID().toString());
    }

    private ArrayList<Post> getSamplePosts() {
        ArrayList<Post> arr = new ArrayList<Post>();
        for (int i=0; i<10; i++) {
            arr.add(getSamplePost());
        }
        return arr;
    }

    private Comment getSampleComment() {
        return new Comment("anony-name",
                "this is comment content...");
    }

    private DatabaseManager() {
        try {
            InputStream serviceAccount = new FileInputStream(K.SERVICE_ACCOUNT_PATH);
            GoogleCredentials credentials = GoogleCredentials.fromStream(serviceAccount);
            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(credentials)
                    .build();
            FirebaseApp.initializeApp(options);
            db = FirestoreClient.getFirestore();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private @Nullable DocumentSnapshot checkEmailExistsAccount(String email) {
        CollectionReference users = db.collection(K.USERS_COLLECTION);
        Query query = users.whereEqualTo("email",email).limit(1);
        ApiFuture<QuerySnapshot> future = query.get();
        try {
            for (DocumentSnapshot documentSnapshot: future.get().getDocuments()) {
                return documentSnapshot;
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Response insertUserToDb(String email, String password, String username) {
        if (checkEmailExistsAccount(email) != null)
            return new Response(false,"This email has been associated with an account.");
        String uuid = UUID.randomUUID().toString();
        DocumentReference docRef = db
                .collection(K.USERS_COLLECTION)
                .document(uuid);
        User user = new User(email,
                password,
                username,
                uuid,
                new ArrayList<>(),
                new ArrayList<>());
        ApiFuture<WriteResult> future = docRef.set(user);
        try {
            System.out.println(future.get().getUpdateTime());
            return new Response(true,null,user);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return new Response(false);
    }

    public Response auth(String email, String psw) {
        DocumentSnapshot user = checkEmailExistsAccount(email);
        if (user == null)
            return new Response(false,"The account with this email address does not exist");
        else if (!user.get("password").equals(psw))
            return new Response(false, "Either your login email or password is incorrect");
        return new Response(true,null,user.toObject(User.class));
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

    public Comment insertComment(String postID, Comment comment) {
        return getSampleComment();
    }

}
