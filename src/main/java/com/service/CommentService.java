package com.service;

import com.model.Comment;
import com.model.Response;
import com.utility.DatabaseManager;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class CommentService {

    public ResponseEntity insertComment(String postID, Comment comment) {
        DatabaseManager.shared.insertComment(postID,comment);
        return ResponseEntity
                .ok()
                .body(new Response<Comment>(true,null,comment));
    }
}
