package com.controller;

import com.model.User;
import com.service.UserAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class UserAuthController {

    @Autowired
    private UserAuthService userAuthService;

    @PostMapping("/signUp")
    public ResponseEntity signup(@RequestBody User user) {
        return userAuthService.insertUser(user.getEmail(),
                user.getPassword(),
                user.getUsername());
    }

    @GetMapping("/login")
    public ResponseEntity login(@RequestParam(value = "email",defaultValue = "") String email,
                                @RequestParam(value = "psw", defaultValue = "") String psw) {
        return userAuthService.verifyUser(email,psw);
    }
}
