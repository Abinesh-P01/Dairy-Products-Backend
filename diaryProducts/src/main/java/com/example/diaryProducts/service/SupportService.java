package com.example.diaryProducts.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.diaryProducts.model.SupportMessage;
import com.example.diaryProducts.repository.SupportMessageRepo;

@Service
public class SupportService {

	@Autowired
	SupportMessageRepo supportMessageRepo;

	public SupportMessage sendMessage(SupportMessage message) {
		message.setStatus("OPEN");
		return supportMessageRepo.save(message);
	}

	public List<SupportMessage> getMessagesByUsername(String username) {
		return supportMessageRepo.findByUsername(username);
	}

	public List<SupportMessage> getAllMessages() {
		return supportMessageRepo.findAll();
	}

	public SupportMessage replyToMessage(int messageId, String reply) {
		SupportMessage message = supportMessageRepo.findById(messageId)
				.orElseThrow(() -> new RuntimeException("Message not found"));

		message.setReply(reply);
		message.setStatus("REPLIED");
		return supportMessageRepo.save(message);
	}
}
