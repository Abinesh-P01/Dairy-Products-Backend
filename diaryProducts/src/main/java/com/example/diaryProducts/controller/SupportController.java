package com.example.diaryProducts.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.diaryProducts.model.SupportMessage;
import com.example.diaryProducts.service.SupportService;

@RestController
public class SupportController {

	@Autowired
	SupportService supportService;

	@PostMapping("/support/send")
	public SupportMessage sendMessage(@RequestBody SupportMessage message) {
		return supportService.sendMessage(message);
	}

	@GetMapping("/support/{username}")
	public List<SupportMessage> getMessages(@PathVariable String username) {
		return supportService.getMessagesByUsername(username);
	}

	@GetMapping("/support/all")
	public List<SupportMessage> getAllMessages() {
		return supportService.getAllMessages();
	}

	@PostMapping("/support/reply/{messageId}")
	public ResponseEntity<SupportMessage> reply(@PathVariable int messageId, @RequestParam String reply) {
		return ResponseEntity.ok(supportService.replyToMessage(messageId, reply));
	}
}
