package com.example.diaryProducts.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.diaryProducts.model.Admin;

public interface AdminRepo extends JpaRepository<Admin, Integer> {
	Optional<Admin> findByUsernameAndPassword(String username, String password);
	Optional<Admin> findByUsername(String username);
}

