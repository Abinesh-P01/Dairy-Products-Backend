package com.example.diaryProducts.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.example.diaryProducts.model.Users;
import com.example.diaryProducts.service.UserService;

@RestController
public class UserController {
	@Autowired
	UserService userService;

	@PostMapping("/userRegister")
	public Users registerUser(@RequestBody Users user) {
		return userService.registerUser(user);
	}

	@PostMapping("/userLogin")
	public String loginUser(@RequestBody Users user) {
		boolean valid = userService.loginUser(user.getUsername(), user.getPassword());
		return valid ? "success" : "failure";
	}
}
