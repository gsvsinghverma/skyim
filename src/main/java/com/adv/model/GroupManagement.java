package com.adv.model;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.Nullable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.adv.util.Constant;
import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "groupmanagement")
public class GroupManagement {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "Group_Name", columnDefinition = "varchar(150) default '' NOT NULL")
	private String groupName;

		@JsonFormat(pattern = Constant.API_RESP_DATE_FORMATE)
	@Column(name = "CREATION_DATE")
	private Timestamp creationDate;

	@JsonFormat(pattern = Constant.API_RESP_DATE_FORMATE)
	@Column(name = "UPDATION_DATE")
	private Timestamp updationDate;
	@Column(name = "ACTIVE", columnDefinition = "boolean default true")
	private boolean active;
	@Column(name = "is_deleted", columnDefinition = "boolean default false")
	private boolean isDeleted;
		
	
	@Column(name = "Group_Description")
	private String groupDescription;
	
	@Column(name = "Group_Image")
	private String groupImage;
	
	
	
public String getGroupDescription() {
		return groupDescription;
	}

	public void setGroupDescription(String groupDescription) {
		this.groupDescription = groupDescription;
	}

	public String getGroupImage() {
		return groupImage;
	}

	public void setGroupImage(String groupImage) {
		this.groupImage = groupImage;
	}


	
	@ManyToMany(cascade=CascadeType.ALL)
	@JoinTable(name = "groupManagement_user", joinColumns = @JoinColumn(name = "groupmangement_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
	@Nullable
	Set<User> users = new HashSet<>();
	
	
		public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}

		public boolean isDeleted() {
		return isDeleted;
	}

	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

		
		public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public Timestamp getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Timestamp creationDate) {
		this.creationDate = creationDate;
	}

	public Timestamp getUpdationDate() {
		return updationDate;
	}

	public void setUpdationDate(Timestamp updationDate) {
		this.updationDate = updationDate;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}



}
