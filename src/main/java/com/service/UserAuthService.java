package com.service;

import com.model.Response;
import com.model.User;
import com.utility.DatabaseManager;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class UserAuthService {

    public ResponseEntity insertUser(String email, String psw, String username) {
        if (!User.isValidEmailAddress(email)) {
            return ResponseEntity
                    .status(502)
                    .body(new Response<>(false,"invalid email"));
        } else if (!User.isValidPassword(psw)) {
            return ResponseEntity
                    .status(502)
                    .body(new Response<>(false,"minimum password length: 8 characters"));
        }

        Response<User> response = DatabaseManager.shared.insertUserToDb(email,psw,username);
        if (!response.getStatus())
            return ResponseEntity
                    .status(502)
                    .body(response);
        return ResponseEntity.
                ok().
                body(response);
    }

    public ResponseEntity verifyUser(String email, String psw) {
        Response<User> response = DatabaseManager.shared.auth(email,psw);
        if (!response.getStatus())
            return ResponseEntity
                    .status(502)
                    .body(response);
        return ResponseEntity
                .ok()
                .body(response);
    }
}
