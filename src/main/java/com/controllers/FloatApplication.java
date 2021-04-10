package com.controllers;

import com.utilities.DatabaseManager;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

import com.models.*;

import java.util.ArrayList;

@SpringBootApplication
@RestController
public class FloatApplication {

	public static void main(String[] args) {
		SpringApplication.run(FloatApplication.class, args);
	}

	// registration and authentication

	@PostMapping("/signup")
	public Response signup(@RequestBody User user) {
		Response res = new Response();
		if (!user.isValidEmailAddress()) {
			res.setStatus(false);
			res.setMessage("invalid email");
		} else if (!user.isValidPassword()) {
			res.setStatus(false);
			res.setMessage("minimum password length: 8 characters");
		} else {
			DatabaseManager.shared.insertUserToDb(user);
			res.setStatus(true);
		}
		return res;
	}

	@GetMapping("/login")
	public Response<User> login(@RequestParam(value = "email",defaultValue = "") String email,
						   @RequestParam(value = "psw", defaultValue = "") String psw) {
		User user = DatabaseManager.shared.auth(email,psw);
		if (user==null) {
			return new Response(false, "Unable to login", null);
		}
		return new Response(true,null,user);
	}

	// create, read, display posts

	@PostMapping("/makePost")
	public Response makePost(@RequestBody Post post) {
		DatabaseManager.shared.insertPostToDbAfter(post.getUserUuid(),post);
		return new Response(true,null,null);
	}

	@GetMapping("/readPost")
	public Response<Post> readPost(@RequestParam(value = "postid",defaultValue = "") String postID) {
		Post post = DatabaseManager.shared.queryPostBy(postID);
		if (post==null) {
			return new Response(false,
					String.format("no post associated with id %s exists",postID),
					null);
		}
		return new Response<Post>(true,null,post);
	}

	@GetMapping("/getPosts")
	public Response<ArrayList<Post>> getPosts(@RequestParam(value = "keyword",defaultValue = "") String keyword) {
		ArrayList<Post> arr = DatabaseManager.shared.queryPostsBy(keyword);
		if (arr == null || arr.isEmpty()) {
			return new Response(false,"none found", null);
		}
		return new Response<ArrayList<Post>>(true,null,arr);
	}

}
