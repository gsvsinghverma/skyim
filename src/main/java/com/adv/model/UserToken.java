package com.adv.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.adv.util.Constant;
import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "user_tokens")
public class UserToken {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@JsonFormat(pattern = Constant.API_RESP_DATE_FORMATE)
	@Column(name = "CREATION_DATE")
	private Timestamp creationDate;

	@Column(name = "Device_Type")
	private String devicetype;
	
	@Column(name = "Token")
	private String token;  
	
	
	@Column(name = "user_name ")
	private String userName;


	public String getUserName() {
		return userName;
	}


	public void setUserName(String userName) {
		this.userName = userName;
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public Timestamp getCreationDate() {
		return creationDate;
	}


	public void setCreationDate(Timestamp creationDate) {
		this.creationDate = creationDate;
	}


	

	public String getDevicetype() {
		return devicetype;
	}


	public void setDevicetype(String devicetype) {
		this.devicetype = devicetype;
	}


	public String getToken() {
		return token;
	}


	public void setToken(String token) {
		this.token = token;
	}



	
	
	
	
}
