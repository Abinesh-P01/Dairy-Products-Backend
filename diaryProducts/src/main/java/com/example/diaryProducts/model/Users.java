package com.example.diaryProducts.model;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Users {
		public Users() {
		}

		public Users(String username, String email, String password) {
			super();
			this.username = username;
			this.email = email;
			this.password = password;
		}

		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private int user_id;
		private String username;
		private String email;
		private String password;

		public int getUser_id() {
			return user_id;
		}
		public void setUser_id(int user_id) {
			this.user_id = user_id;
		}
		public String getUsername() {
			return username;
		}
		public void setUsername(String username) {
			this.username = username;
		}
		public String getEmail() {
			return email;
		}
		public void setEmail(String email) {
			this.email = email;
		}
		public String getPassword() {
			return password;
		}
		public void setPassword(String password) {
			this.password = password;
		}

		@Override
		public String toString() {
			return "Users [user_id=" + user_id + ", username=" + username + ", email=" + email + ", password="
					+ password + "]";
		}
		
	}

	
