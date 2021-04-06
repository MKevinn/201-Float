package com.controllers;

import com.models.Response;
import com.models.User;
import com.utilities.DatabaseManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

public class AuthController extends FloatApplication {
    @GetMapping("/signup")
    public Response signup(@RequestParam(value = "name",defaultValue = "") String name,
                           @RequestParam(value = "email",defaultValue = "") String email,
                           @RequestParam(value = "psw", defaultValue = "") String psw) {
        User user = new User(email, psw, name);
        Response res = new Response();
        if (!user.isValidEmailAddress()) {
            res.setStatus(false);
            res.setMessage("invalid email");
        } else if (!user.isValidPassword()) {
            res.setStatus(false);
            res.setMessage("invalid password");
        } else {
            DatabaseManager.shared.insertUserToDatabase(user);
            res.setStatus(true);
        }
        return res;
    }

    @GetMapping("/login")
    public Response signup(@RequestParam(value = "email",defaultValue = "") String email,
                           @RequestParam(value = "psw", defaultValue = "") String psw) {
        Response res = new Response(DatabaseManager.shared.auth(email,psw),null,null);
        return res;
    }
}
