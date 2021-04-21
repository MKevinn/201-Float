package com.utility;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import com.model.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

import com.google.auth.oauth2.GoogleCredentials;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.core.io.ClassPathResource;
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
        ArrayList<Post> arr = new ArrayList<>();
        for (int i=0; i<10; i++) {
            arr.add(getSamplePost());
        }
        return arr;
    }

    private DatabaseManager() {
        try {
            InputStream serviceAccount = new ClassPathResource(K.SERVICE_ACCOUNT_FILENAME).getInputStream();
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

    public Response createPost(String content, String anonymousPosterName, 
    							String userUuid) {
        String uuid = UUID.randomUUID().toString();
        DocumentReference docRef = db
                .collection(K.POSTS_COLLECTION)
                .document(uuid);
        Post post = new Post(uuid, 
			        		content, new ArrayList<>(), 0, 
			        		new ArrayList<>() , 
			        		anonymousPosterName, 
			        		userUuid);
        ApiFuture<WriteResult> future = docRef.set(post);
        insertPostToDbAfterUser(userUuid,uuid);
        try {
            System.out.println(future.get().getUpdateTime());
            return new Response(true,null,post);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    	return new Response(false);
    }

    // insert the newly created post's postID in the poster's postIDs' array
    public void insertPostToDbAfterUser(String userID, String postID) {
        DocumentReference docRef = db
                .collection(K.USERS_COLLECTION)
                .document(userID);
        ApiFuture<WriteResult> future = docRef.update(K.USERS_POSTIDS_FIELD, FieldValue.arrayUnion(postID));
        try {
            System.out.println(postID+" appended to array. "+future.get().getUpdateTime());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }
    
    public Response insertComment(String anonymousPosterName, String content, String postID) {
    	DocumentReference docRef = db
                .collection(K.POSTS_COLLECTION)
                .document(postID);
    	Comment comment = new Comment(anonymousPosterName, content);
    	// append the newly created comment to post's comments array
    	ApiFuture<WriteResult> future = docRef.update(K.POSTS_COMMENTS_FIELD, FieldValue.arrayUnion(comment));
    	try {
            System.out.println(future.get().getUpdateTime());
            return new Response(true,null,comment);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    	return new Response(false);
    }

    public Post queryPostByID(String postID) {
        // get a post
    	DocumentReference docRef = db
    			.collection(K.POSTS_COLLECTION)
    			.document(postID);
    	ApiFuture<DocumentSnapshot> future = docRef.get();
    	DocumentSnapshot document;
    	Post post = null;
		try {
			document = future.get();
			if (document.exists()) {
				post = document.toObject(Post.class);
				System.out.println("Document data: " + document.getData());
				post.setComments(getCommentsForPost(postID));
				return post;
	    	} else {
	    		System.out.println("No such document!");
	   		}
		} catch (InterruptedException | ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    	return post;
    }

    // TODO
    public Response like(String postID, String userID) {
        // append the postID to user's likedPostIDs array
        return null;
    }

    // TODO
    public Response dislike(String postID, String userID) {
        // remove the postID from user's likedPostIDs array
        return null;
    }

    public ArrayList<Post> queryPostsBy(String keyword) {
        // get posts
        return getSamplePosts();
    }
    
    public ArrayList<Comment> getCommentsForPost(String postID) {
    	CollectionReference commentRef = db
    			.collection(K.POSTS_COLLECTION + "/" + postID + "/comments");
    	ApiFuture<QuerySnapshot> postComments = commentRef.get();
    	List<QueryDocumentSnapshot> documents;
    	ArrayList<Comment> postCommentData = new ArrayList<>();
    	try {
			documents = postComments.get().getDocuments();
			for (QueryDocumentSnapshot document : documents) {
				Comment comment;
				comment = document.toObject(Comment.class);
				System.out.println("document data comments" + document.getData());
				postCommentData.add(comment);
			}
		} catch (InterruptedException | ExecutionException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    	return postCommentData;
    }
}
