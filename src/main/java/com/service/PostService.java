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
    	Response<Post> response = DatabaseManager.shared.createPost(post.getContent(), post.getAnonymousPosterName(), post.getUserUuid());
    	if (!response.getStatus())
            return ResponseEntity
                    .badRequest()
                    .body(response);
        return ResponseEntity.
                ok().
                body(response);
    }

    public ResponseEntity readPost(String postID) {
        Post post = DatabaseManager.shared.queryPostByID(postID);
        if (post==null) {
            return ResponseEntity
                    .badRequest()
                    .body(new Response(false,
                            String.format("no post associated with id %s exists",postID)));
        }
        return ResponseEntity
                .ok()
                .body(new Response<Post>(true,null,post));
    }

    public ResponseEntity getPosts(String keyword) {
        ArrayList<Post> arr = DatabaseManager.shared.queryPostsBy(keyword);
        if (arr == null || arr.isEmpty()) {
            return ResponseEntity
                    .badRequest()
                    .body(new Response(false,"none found"));
        }
        return ResponseEntity
                .ok()
                .body(new Response<ArrayList>(true,null,arr));
    }

}
