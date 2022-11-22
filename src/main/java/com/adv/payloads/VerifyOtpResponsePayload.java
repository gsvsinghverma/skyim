package com.adv.payloads;

import java.util.List;

public class VerifyOtpResponsePayload {

	private String message;
	private String status;
	private Object data;
	private List<ProjectContactsResponse> contacts;
	private String token;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public List<ProjectContactsResponse> getContacts() {
		return contacts;
	}

	public void setContacts(List<ProjectContactsResponse> contacts) {
		this.contacts = contacts;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

}