package com.example.diaryProducts.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.diaryProducts.model.SupportMessage;

public interface SupportMessageRepo extends JpaRepository<SupportMessage, Integer> {

	List<SupportMessage> findByUsername(String username);
}
