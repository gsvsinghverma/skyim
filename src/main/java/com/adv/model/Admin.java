package com.adv.model;

import java.sql.Timestamp;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.adv.util.Constant;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "admin")
public class Admin {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "USER_NAME", columnDefinition = "varchar(150) default '' NOT NULL")
	private String username;

	@Column(name = "FIRST_NAME", columnDefinition = "varchar(100) default '' NOT NULL")
	private String firstName;

	@Column(name = "LAST_NAME", columnDefinition = "varchar(100) default '' NOT NULL")
	private String lastName;

	@JsonIgnore
	@Column(name = "PASSWORD", columnDefinition = "varchar(255) default '' NOT NULL")
	private String password;

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
	

	public boolean isDeleted() {
		return isDeleted;
	}

	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	@Column(name = "PROFILE_PHOTO", columnDefinition = "varchar(255) default '' NOT NULL")
	private String profilePhoto;

	@Column(name = "MOBILE_NUMBER", unique = true, columnDefinition = "varchar(255) default '' NOT NULL ")
	private String mobileNumber;

	@Column(name = "EMAIL", columnDefinition = "varchar(255) default '' NOT NULL")
	private String email;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "DESIGNATION_ID", referencedColumnName = "id")
	private Designation designation;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "LOCATION_ID", referencedColumnName = "id")
	private Location location;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "UNIT_ID", referencedColumnName = "id")
	private Unit unit;
    
	
	@OneToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
	@JoinColumn(name = "ADMIN_ROLE_ID", referencedColumnName = "id")
	private AdminRoles role;

	@Column(name = "ismasteradmin", columnDefinition = "boolean default false")
	private boolean ismasteradmin;

	
	public boolean isIsmasteradmin() {
		return ismasteradmin;
	}

	public void setIsmasteradmin(boolean ismasteradmin) {
		this.ismasteradmin = ismasteradmin;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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

	public String getProfilePhoto() {
		return profilePhoto;
	}

	public void setProfilePhoto(String profilePhoto) {
		this.profilePhoto = profilePhoto;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Designation getDesignation() {
		return designation;
	}

	public void setDesignation(Designation designation) {
		this.designation = designation;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public Unit getUnit() {
		return unit;
	}

	public void setUnit(Unit unit) {
		this.unit = unit;
	}

	public AdminRoles getRole() {
		return role;
	}

	public void setRole(AdminRoles role) {
		this.role = role;
	}

}
