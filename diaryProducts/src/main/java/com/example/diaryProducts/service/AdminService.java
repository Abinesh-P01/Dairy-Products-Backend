package com.example.diaryProducts.service;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.diaryProducts.model.Admin;
import com.example.diaryProducts.repository.AdminRepo;

@Service
public class AdminService {
	@Autowired
	AdminRepo adminRepo;

	public Admin registerAdmin(Admin admin) {
		return adminRepo.save(admin);
	}

	public boolean loginAdmin(String username, String password) {
		Optional<Admin> admin = adminRepo.findByUsernameAndPassword(username, password);
		return admin.isPresent();
	}
}
