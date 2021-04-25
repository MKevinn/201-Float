package com.service;

import com.model.Comment;
import com.model.Response;
import com.utility.DatabaseManager;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class NotificationService {

    public ResponseEntity getNotifications(String userID) {
        Response<ArrayList<Comment>> response = DatabaseManager.shared.getNotifications(userID);
        if (!response.getStatus()) {
            return ResponseEntity
                    .status(502)
                    .body(response);
        }
        return ResponseEntity
                .ok()
                .body(response);
    }

    public ResponseEntity removeNotifications(String notificationID) {
        Response response = DatabaseManager.shared.removeNotification(notificationID);
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
