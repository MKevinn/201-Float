package com.service;

import com.model.Post;
import com.model.Response;
import com.utility.DatabaseManager;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class PostService {

    public ResponseEntity insertPost(Post post) {
    	Response<Post> response = DatabaseManager.shared.createPost(post);
    	if (!response.getStatus())
            return ResponseEntity
                    .status(502)
                    .body(response);
        return ResponseEntity.
                ok().
                body(response);
    }
    
    public ResponseEntity likePost(String postID, String userID) {
    	 Response response = DatabaseManager.shared.like(postID, userID);
         if (response.getStatus()) {
             return ResponseEntity
                     .ok()
                     .body(response);
         }
         return ResponseEntity
                 .status(502)
                 .body(response);
    }
    
    public ResponseEntity dislikePost(String postID, String userID) {
    	 Response response = DatabaseManager.shared.dislike(postID, userID);
         if (response.getStatus()) {
             return ResponseEntity
                     .ok()
                     .body(response);
         }
         return ResponseEntity
                 .status(502)
                 .body(response);
    }

    public ResponseEntity readPost(String postID) {
        Post post = DatabaseManager.shared.queryPostByID(postID);
        if (post==null) {
            return ResponseEntity
                    .status(502)
                    .body(new Response(false,
                            String.format("no post associated with id %s exists",postID)));
        }
        return ResponseEntity
                .ok()
                .body(new Response<>(true,null,post));
    }

    public ResponseEntity getPosts(String keyword, String tags) {
        ArrayList<Post> arr = DatabaseManager.shared.queryPostsBy(keyword, tags);
        if (arr == null || arr.isEmpty()) {
            return ResponseEntity
                    .status(502)
                    .body(new Response(false,"none found"));
        }
        return ResponseEntity
                .ok()
                .body(new Response<ArrayList>(true,null,arr));
    }

}
