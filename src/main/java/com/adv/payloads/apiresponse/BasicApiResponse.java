package com.adv.payloads.apiresponse;

import org.springframework.http.HttpStatus;

public class BasicApiResponse {

	private boolean status;
	private String message;
	private Object data;
	private String token;
    private HttpStatus httpstatus;
    private  String passwordPolicyMessage;
 
	public HttpStatus getHttpstatus() {
	return httpstatus;
}

public void setHttpstatus(HttpStatus httpstatus) {
	this.httpstatus = httpstatus;
}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public String getPasswordPolicyMessage() {
		return passwordPolicyMessage;
	}

	public void setPasswordPolicyMessage(String passwordPolicyMessage) {
		this.passwordPolicyMessage = passwordPolicyMessage;
	}
	
	
	
	

}
