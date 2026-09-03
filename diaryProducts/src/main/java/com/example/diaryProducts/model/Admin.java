package com.example.diaryProducts.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Admin {
	public Admin() {
	}

	public Admin(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}

	@Id
	@GeneratedValue(strategy = 	GenerationType.IDENTITY)
	private int admin_id;
	@Column(nullable = false)
	private String username;
	@Column(nullable = false)
	private String password;

	public int getAdmin_id() {
		return admin_id;
	}
	public void setAdmin_id(int admin_id) {
		this.admin_id = admin_id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
}

