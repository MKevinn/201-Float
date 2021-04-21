package com.controller;

import com.model.Comment;
import com.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping("/make/{postID}")
    public ResponseEntity makeComment(@PathVariable String postID,
                                      @RequestBody Comment comment) {
        return commentService.insertComment(postID, comment);
    }
}