package com.controllers;

import com.utilities.DatabaseManager;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

import com.models.*;

@SpringBootApplication
@RestController
public class FloatApplication {

	public static void main(String[] args) {
		SpringApplication.run(FloatApplication.class, args);
	}

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
