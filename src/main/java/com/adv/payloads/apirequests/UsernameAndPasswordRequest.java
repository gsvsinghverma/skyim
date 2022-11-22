package com.adv.payloads.apirequests;

public class UsernameAndPasswordRequest {

	private String username;
	private String newPassword;
	private String password;

		public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

		public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}


}