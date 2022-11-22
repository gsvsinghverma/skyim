package com.adv.payloads.apirequests;

public class Useractionrequest {

	private String username;
	private String action;
	private String forwardnumber;
	private String sourceUsername;
	private String destinationUsername;

	public String getSourceUsername() {
		return sourceUsername;
	}

	public void setSourceUsername(String sourceUsername) {
		this.sourceUsername = sourceUsername;
	}

	public String getDestinationUsername() {
		return destinationUsername;
	}

	public void setDestinationUsername(String destinationUsername) {
		this.destinationUsername = destinationUsername;
	}

	public String getForwardnumber() {
		return forwardnumber;
	}

	public void setForwardnumber(String forwardnumber) {
		this.forwardnumber = forwardnumber;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

}
