package com.adv.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "auditlog")
public class AuditLog {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;

	@Column(name = "USER_NAME")
	private String userName;

	@Column(name = "MODULE_NAME")
	private String moduleName;
	

	
	@Column(name = "DESCRIPTION")
	private String description;
	
	@Column(name = "Action")
	private String action;
	
	
	@JsonFormat(pattern = "MM-dd-yyyy HH:mm:ss")
	@Column(name = "CREATION_DATE")
	private Timestamp creationDate;
	


	@Column(name = "WEB_MOBILE")
private String webmobile;




	

	
	public Long getId() {
		return id;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getWebmobile() {
		return webmobile;
	}

	public void setWebmobile(String webmobile) {
		this.webmobile = webmobile;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Timestamp getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Timestamp creationDate) {
		this.creationDate = creationDate;
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}



	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

}
