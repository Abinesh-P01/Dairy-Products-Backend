package com.example.diaryProducts.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class SupportMessage {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int message_id;

	private String username;

	@Column(length = 2000)
	private String message;

	private String sender;

	@Column(length = 2000)
	private String reply;

	private String status;

	public SupportMessage() {
	}

	public SupportMessage(String username, String message, String sender, String status) {
		this.username = username;
		this.message = message;
		this.sender = sender;
		this.status = status;
	}

	public int getMessage_id() {
		return message_id;
	}

	public void setMessage_id(int message_id) {
		this.message_id = message_id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getReply() {
		return reply;
	}

	public void setReply(String reply) {
		this.reply = reply;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
