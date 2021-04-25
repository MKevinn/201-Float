package com.service;

import com.model.Response;
import com.utility.DatabaseManager;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class TagService {

    public ResponseEntity getAllTags() {
        Response response = DatabaseManager.shared.getAllTags();
        if (!response.getStatus())
            return ResponseEntity
                    .status(502)
                    .body(response);
        return ResponseEntity
                .ok()
                .body(response);
    }
}
