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

	@PostMapping("/signup")
	public Response signup(@RequestBody User user) {
		Response res = new Response();
		if (!user.isValidEmailAddress()) {
			res.setStatus(false);
			res.setMessage("invalid email");
		} else if (!user.isValidPassword()) {
			res.setStatus(false);
			res.setMessage("minimum password length: 8");
		} else {
			DatabaseManager.shared.insertUserToDatabase(user);
			res.setStatus(true);
		}
		return res;
	}

	@GetMapping("/login")
	public Response<User> signup(@RequestParam(value = "email",defaultValue = "") String email,
						   @RequestParam(value = "psw", defaultValue = "") String psw) {
		User user = DatabaseManager.shared.auth(email,psw);
		if (user==null) {
			return new Response(false, "Unable to login", null);
		}
		return new Response(true,null,user);
	}
}
