package com.adv.payloads.apirequests;

public class AdminResetPasswordRequest {
	private String username;
	private String newPassword;	
		public String getNewPassword() {
		return newPassword;
	}
	public String getUsername() {
			return username;
		}
		public void setUsername(String username) {
			this.username = username;
		}
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	
}
