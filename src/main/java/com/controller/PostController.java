package com.controller;

import com.model.Post;
import com.model.Response;
import com.service.PostService;
import com.utility.DatabaseManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/post")
public class PostController {

    @Autowired
    private PostService postService;

    @PostMapping("/make")
    public ResponseEntity makePost(@RequestBody Post post) {
        return postService.insertPost(post);
    }

    @PostMapping("/like")
    public ResponseEntity likePost(@RequestBody Post post) {
        return postService.likePost(post.getPostID(), post.getUserUuid());
    }
    
    @PostMapping("/dislike")
    public ResponseEntity dislikePost(@RequestBody Post post) {
        return postService.dislikePost(post.getPostID(), post.getUserUuid());
    }
    
    @GetMapping("/read")
    public ResponseEntity readPost(@RequestParam(value = "postid",defaultValue = "") String postID) {
        return postService.readPost(postID);
    }

    @GetMapping("/get")
    public ResponseEntity getPosts(@RequestParam(value = "keyword",defaultValue = "") String keyword,
                                   @RequestParam(value = "tags", defaultValue = "") String tags) {
        return postService.getPosts(keyword, tags);
    }
}
