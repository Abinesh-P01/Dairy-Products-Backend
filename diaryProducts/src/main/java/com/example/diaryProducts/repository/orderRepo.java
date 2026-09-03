package com.example.diaryProducts.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.diaryProducts.model.orders;

public interface orderRepo extends JpaRepository<orders,Integer>{

	List<orders> findByUsername(String username);

}
