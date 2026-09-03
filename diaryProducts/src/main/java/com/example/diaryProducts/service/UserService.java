package com.example.diaryProducts.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.diaryProducts.model.Users;
import com.example.diaryProducts.repository.UsersRepo;

@Service
public class UserService {
	@Autowired
	UsersRepo usersRepo;

	public Users registerUser(Users user) {
		return usersRepo.save(user);
	}

	public boolean loginUser(String username, String password) {
		Optional<Users> user = usersRepo.findByUsernameAndPassword(username, password);
		System.out.println(user);
		return user.isPresent();
	}
	public List<Users> getAllUsers() {
		return usersRepo.findAll();
	}
}
