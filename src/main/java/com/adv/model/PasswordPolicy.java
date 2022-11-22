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

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="passwordpolicy")
public class PasswordPolicy {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Integer passwordFor;
	private  Integer  passwordDuration;
	@JsonFormat(pattern = Constant.API_RESP_DATE_FORMATE)
	private  Timestamp  validTill;
	@JsonFormat(pattern = Constant.API_RESP_DATE_FORMATE)
	@Column(name = "CREATION_DATE")
	private Timestamp creationDate;
	@JsonFormat(pattern = Constant.API_RESP_DATE_FORMATE)
	@Column(name = "UPDATION_DATE")
	private Timestamp updationDate;
	private Integer minPasswordLength;
	private Integer maxPasswordLength;
	private Integer upperCaseletters;
	private Integer lowerCaseletters;
	private Integer number;
	private  Integer specialCharacters;
	private boolean lastPasswordCheck;
	private boolean whiteSpaceCheck;
	

	
	
	

}


