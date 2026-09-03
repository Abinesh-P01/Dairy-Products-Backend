package com.example.diaryProducts.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.example.diaryProducts.model.Admin;
import com.example.diaryProducts.model.Users;
import com.example.diaryProducts.service.AdminService;
import com.example.diaryProducts.service.UserService;

import jakarta.validation.Valid;

@RestController
public class AdminController {
	@Autowired
	AdminService adminService;
	
	@Autowired
	UserService userService;
	
	@PostMapping("/adminRegister")
	public Admin registerAdmin(@RequestBody Admin admin) {
		return adminService.registerAdmin(admin);
	}
	@PostMapping("/adminLogin")
	public String loginAdmin(@Valid@RequestBody Admin admin) {
		boolean valid = adminService.loginAdmin(admin.getUsername(), admin.getPassword());
		return valid ? "success" : "failure";
	}
	@GetMapping("/admin/allUsers")
	public List<Users> getAllUsers() {
		return userService.getAllUsers();
	}
}
