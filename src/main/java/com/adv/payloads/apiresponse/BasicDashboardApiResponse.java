package com.adv.payloads.apiresponse;
import org.springframework.http.HttpStatus;

public class BasicDashboardApiResponse {
	private boolean status;
	private String message;
	private Object data;

    private HttpStatus httpstatus;
  
 
	public HttpStatus getHttpstatus() {
	return httpstatus;
}

public void setHttpstatus(HttpStatus httpstatus) {
	this.httpstatus = httpstatus;
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

}
