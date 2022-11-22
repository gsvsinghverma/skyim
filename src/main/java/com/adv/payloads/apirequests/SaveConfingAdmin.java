package com.adv.payloads.apirequests;

public class SaveConfingAdmin {

	private String username;
	private String email;
	private String user;
	private String creationDate;
	private String emailId;
	private String UpdationDate;
	private long id;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getUpdationDate() {
		return UpdationDate;
	}

	public void setUpdationDate(String updationDate) {
		UpdationDate = updationDate;
	}

}
