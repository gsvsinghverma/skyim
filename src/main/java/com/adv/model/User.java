package com.adv.model;

import java.sql.Timestamp;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "user")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "USER_NAME", unique = true, columnDefinition = "varchar(150) default '' NOT NULL")
	private String username;

	
	@Column(name = "MOBILE_NUMBER", columnDefinition = "varchar(20) default '' NOT NULL",unique = true)
	private String mobileNumber;
	
	@Column(name = "OTP", columnDefinition = "varchar(255) default '' ")
	private String otp;

	@Column(name = "EMAIL_ID", columnDefinition = "varchar(100) default '' NOT NULL")
	private String emailId;

	@JsonIgnore
	@Column(name = "DISPLAY_NAME", columnDefinition = "varchar(150) default '' NOT NULL")
	private String displayName;

	@JsonIgnore
	@Column(name = "LOGIN_USER_NAME", unique = true, columnDefinition = "varchar(150) NOT NULL")
	private String loginUsername;

	@Column(name = "OTP_ENABLE", columnDefinition = "boolean default true")
	private boolean otpEnable;

	@JsonIgnore
	@Column(name = "OTP_TYPE", columnDefinition = "integer default 0")
	private int otpType;

	@JsonIgnore
	@Column(name = "PASSWORD", columnDefinition = "varchar(255) default '' NOT NULL")
	private String password;

	
	@Column(name = "FIRST_NAME", columnDefinition = "varchar(100) default '' NOT NULL")
	private String firstName;

	@Column(name = "LAST_NAME", columnDefinition = "varchar(100) default '' NOT NULL")
	private String lastName;

	@Column(name = "PASSWORD_STATUS", columnDefinition = "integer default 1")
	private int passwordStatus;

	@JsonFormat(shape = JsonFormat.Shape.NUMBER)
	@Column(name = "CREATION_DATE")
	private Timestamp creationDate;

	@JsonFormat(shape = JsonFormat.Shape.NUMBER)
	@Column(name = "UPDATION_DATE")
	private Timestamp updationDate;

	@Column(name = "ACTIVE", columnDefinition = "boolean default true")
	private boolean active;

	@Column(name = "DND_STATUS", columnDefinition = "boolean default false")
	private boolean dndStatus;

	@Column(name = "VERIFICATION_STATUS", columnDefinition = "boolean default false")
	private boolean verificationStatus;

	@Column(name = "CALL_FORWARDING_STATUS", columnDefinition = "boolean default false")
	private boolean callForwardingStatus;

	@Column(name = "CALL_RECORDING_STATUS", columnDefinition = "boolean default false")
	private boolean callRecordingStatus;

	@Column(name = "VIRTUAL_NUMBER", columnDefinition = "varchar(100) default '' ")
	private String virtualNumber;

	@JsonIgnore
	@Column(name = "OUTBOUND_CID", columnDefinition = "varchar(100) default 'ithub' ")
	private String outboundCid;

	@Column(name = "FACEBOOK_ID", columnDefinition = "varchar(255) default '' ")
	private String facebookId;

	@Column(name = "GOOGLE_ID", columnDefinition = "varchar(255) default '' ")
	private String googleId;

	@Column(name = "PROFILE_PHOTO", columnDefinition = "varchar(255) default '' NOT NULL")
	private String profilePhoto;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "DESIGNATION_ID", referencedColumnName = "id")
	private Designation designation;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "LOCATION_ID", referencedColumnName = "id")
	private Location location;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "UNIT_ID", referencedColumnName = "id")
	private Unit unit;

	@JsonIgnore
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "ADMIN_ROLE_ID", referencedColumnName = "id")
	private AdminRoles role;
	
	@Column(name = "isAdmin", columnDefinition = "boolean default false")
	private boolean isAdmin;

	@Column(name = "is_deleted", columnDefinition = "boolean default false")
	private boolean isDeleted;
	
	@Column(name = "isBlocked", columnDefinition = "boolean default false")
	private boolean isBlocked;
	
	
	
	
	
	
	@JsonIgnore
	@Transient
	private String customValue;

	@JsonIgnore
	@Transient
	private String sendEnable;
	

	@Transient
	@JsonIgnore
	private Integer pages;

	@Transient
	@JsonIgnore
	private Integer size;

	@Transient
	@JsonIgnore
	private Integer totalPages;
     
	@Transient
	@JsonIgnore
	private Integer totalElements;

}