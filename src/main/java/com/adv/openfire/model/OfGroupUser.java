package com.adv.openfire.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@IdClass(OfGroupUserId.class)
@Table(name = "ofGroupUser")
public class OfGroupUser {

	@Id
	@Column(name = "groupName", columnDefinition = "varchar(50) NOT NULL")
	private String groupName;

	@Id
	@Column(name = "username", columnDefinition = "varchar(100) NOT NULL")
	private String username;

	@Id
	@Column(name = "administrator", columnDefinition = "boolean default true NOT NULL")
	private boolean administrator;

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public boolean isAdministrator() {
		return administrator;
	}

	public void setAdministrator(boolean administrator) {
		this.administrator = administrator;
	}

}