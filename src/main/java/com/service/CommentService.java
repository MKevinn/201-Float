package com.service;

import com.model.Comment;
import com.model.Response;
import com.utility.DatabaseManager;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class CommentService {

    public ResponseEntity insertComment(String postID, Comment comment) {
        Response response = DatabaseManager.shared.insertComment(comment.getAnonymousPosterName(),
        		comment.getContent(), postID);
        if (response.getStatus()) {
            return ResponseEntity
                    .ok()
                    .body(response);
        }
        return ResponseEntity
                .status(502)
                .body(response);
    }
}
