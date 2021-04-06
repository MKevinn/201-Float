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

}
