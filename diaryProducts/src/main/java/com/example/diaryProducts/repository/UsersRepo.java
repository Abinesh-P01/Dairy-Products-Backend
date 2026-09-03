package com.example.diaryProducts.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.diaryProducts.model.Users;

public interface UsersRepo extends JpaRepository<Users, Integer> {
	Optional<Users> findByUsernameAndPassword(String username, String password);
	Optional<Users> findByUsername(String username);
}
