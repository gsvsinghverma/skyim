package com.adv.payloads.apirequests;

public class UserLoginRequest {

	private String userid;
	private String loginUsername;
	private String dispayname;

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getLoginUsername() {
		return loginUsername;
	}

	public void setLoginUsername(String loginUsername) {
		this.loginUsername = loginUsername;
	}

	public String getDispayname() {
		return dispayname;
	}

	public void setDispayname(String dispayname) {
		this.dispayname = dispayname;
	}

}
