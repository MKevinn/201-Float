package com.utility;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import com.model.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.concurrent.ExecutionException;

import com.google.auth.oauth2.GoogleCredentials;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.core.io.ClassPathResource;
import org.springframework.lang.Nullable;

public class DatabaseManager {

    public static DatabaseManager shared = new DatabaseManager();

    private Firestore db;

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

    // authentication service methods:

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

    // Tag service methods:

    public Response getAllTags() {
        DocumentReference docRef = db
                .collection(K.TAGS_COLLECTION)
                .document("a");
        ApiFuture<DocumentSnapshot> future = docRef.get();
        ArrayList<String> arr;
        try {
            arr = (ArrayList<String>) future.get().get("a");
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return new Response(false,"error occurred when getting tags");
        }
        return new Response(true,null,arr);
    }

    private void insertTag(ArrayList<String> tags) {
        DocumentReference docRef = db
                .collection(K.TAGS_COLLECTION)
                .document("a");
        for (String tag: tags) {
            docRef.update("a", FieldValue.arrayUnion(tag));
        }
    }

    // Post service methods:

    public Response createPost(Post post) {
        String uuid = UUID.randomUUID().toString();
        DocumentReference docRef = db
                .collection(K.POSTS_COLLECTION)
                .document(uuid);
        Post newPost = new Post(uuid,
			        		post.getContent(),
                            post.getTags(),
                            0,
			        		new ArrayList<>() ,
			        		post.getAnonymousPosterName(),
			        		post.getUserUuid(),
                            new Date());
        ApiFuture<WriteResult> future = docRef.set(newPost);
        insertPostToDbAfterUser(post.getUserUuid(),uuid);
        insertTag(post.getTags());
        try {
            System.out.println(future.get().getUpdateTime());
            return new Response(true,null,newPost);
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

    public Post queryPostByID(String postID) {
        // get a post
        DocumentReference docRef = db
                .collection(K.POSTS_COLLECTION)
                .document(postID);
        ApiFuture<DocumentSnapshot> future = docRef.get();
        DocumentSnapshot document;
        Post post;
        try {
            document = future.get();
            if (document.exists()) {
                post = document.toObject(Post.class);
                System.out.println("Document data: " + document.getData());
                return post;
            } else {
                System.out.println("No such document!");
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Response like(String postID, String userID) {
        // append the postID to user's likedPostIDs array
    	DocumentReference postRef = db
                .collection(K.POSTS_COLLECTION)
                .document(postID);
    	DocumentReference userRef = db
    			.collection(K.USERS_COLLECTION)
                .document(userID);
    	ApiFuture<WriteResult> future = postRef.update("likedCount", FieldValue.increment(1));
    	try {
            System.out.println("post " + postID + " is liked "+ future.get().getUpdateTime());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return new Response(false, "an internal error occurred when liking post");
        }
    	ApiFuture<WriteResult> userFuture = userRef.update(K.USERS_LIKEDPOSTIDS_FIELD, FieldValue.arrayUnion(postID));
    	try {
            System.out.println("user liked " + userFuture.get().getUpdateTime());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return new Response(false, "an internal error occurred when liking post");
        }
    	return new Response(true);
    }

    public Response dislike(String postID, String userID) {
        // remove the postID from user's likedPostIDs array
    	DocumentReference postRef = db
                .collection(K.POSTS_COLLECTION)
                .document(postID);
    	DocumentReference userRef = db
                .collection(K.USERS_COLLECTION)
                .document(userID);
    	ApiFuture<WriteResult> future = postRef.update("likedCount", FieldValue.increment(-1));
    	try {
            System.out.println("post " + postID + " is disliked "+ future.get().getUpdateTime());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return new Response(false, "an internal error occurred when disliking post");
        }
    	ApiFuture<WriteResult> userFuture = userRef.update(K.USERS_LIKEDPOSTIDS_FIELD, FieldValue.arrayRemove(postID));
    	try {
            System.out.println("user disliked "+ userFuture.get().getUpdateTime());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return new Response(false, "an internal error occurred when disliking post");
        }
    	return new Response(true);
    }

    public ArrayList<Post> queryPostsBy(String keyword, String tagsString) {
        // get posts
    	ArrayList<Post> posts = new ArrayList<>();
    	CollectionReference collectionReference = db.collection(K.POSTS_COLLECTION);
    	Query query = collectionReference
                .limit(100)
                .orderBy("timestamp", Query.Direction.DESCENDING);
    	ApiFuture<QuerySnapshot> querySnapshot = query.get();
    	String[] tags = tagsString.split(",");
    	try {
			for (DocumentSnapshot document: querySnapshot.get().getDocuments()) {
			    Post post = document.toObject(Post.class);
			    if (!post.getContent().contains(keyword.toLowerCase())
                        && !post.getContent().contains(keyword.toUpperCase())
                        && !post.getTags().contains(keyword)) continue;
			    if (tagsString.equals("")) {
                    posts.add(post);
                } else {
                    for (String tag: tags) {
                        if (post.getTags().contains(tag.toLowerCase())
                                || post.getTags().contains(tag.toUpperCase())) {
                            posts.add(post);
                            break;
                        }
                    }
                }
			}
			return posts;
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
    	return null;
    }

    // Comment service methods:

    // add the comment object just created to 'notifications' collection with an according userID(post's poster) field
    public Response insertComment(String anonymousPosterName, String content, String postID) {
        String commentId = UUID.randomUUID().toString();
    	DocumentReference docRef = db
                .collection(K.POSTS_COLLECTION)
                .document(postID);
        ApiFuture<DocumentSnapshot> future = docRef.get();
        Comment comment = new Comment(commentId, anonymousPosterName, content);
        try {
            // append the newly created comment to post's comments array
            docRef.update(K.POSTS_COMMENTS_FIELD, FieldValue.arrayUnion(comment));

            String userUuidToNotify = future.get().getString(K.POSTS_USERID_FIELD);
            insertCommentToNotificationsCollection(userUuidToNotify, postID, comment);
            return new Response(true,null,comment);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return new Response(false, "an internal error occurred when inserting comment");
    }

    private void insertCommentToNotificationsCollection(String userUuidToNotify, String postID, Comment comment) {
        String notificationID = UUID.randomUUID().toString();
        DocumentReference docRef = db
                .collection(K.NOTIFICATIONS_COLLECTION)
                .document(notificationID);
        Notification notification = new Notification(notificationID,
                postID,
                userUuidToNotify,
                comment.getAnonymousPosterName(),
                comment.getContent());
        ApiFuture<WriteResult> future = docRef.set(notification);
        try {
            System.out.println("notification inserted" + future.get().getUpdateTime());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    public Response getNotifications(String userID) {
        CollectionReference ref = db.collection(K.NOTIFICATIONS_COLLECTION);
        Query query = ref.whereEqualTo(K.NOTIFICATIONS_USERID_FIELD,userID);
        ApiFuture<QuerySnapshot> future = query.get();
        try {
            ArrayList<Notification> arr = new ArrayList<>();
            for (DocumentSnapshot documentSnapshot: future.get().getDocuments()) {
                arr.add(documentSnapshot.toObject(Notification.class));
            }
            return new Response(true,null,arr);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return new Response(false,"no notification found");
    }

    public Response removeNotification(String notificationID) {
        DocumentReference docRef = db
                .collection(K.NOTIFICATIONS_COLLECTION)
                .document(notificationID);
        ApiFuture<WriteResult> future = docRef.delete();
        try {
            System.out.println("notification read: "+future.get().getUpdateTime());
            return new Response(true);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return new Response(false);
    }

}
