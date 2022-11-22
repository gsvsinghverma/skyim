package com.adv.payloads.apirequests;

import org.hibernate.validator.constraints.Range;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SavePasswordPolicy {
	

	private Integer passwordFor;
	private  Integer  passwordDuration;
	@Range(min = 1l, message = "Please select positive numbers Only")
	private Integer minPasswordLength;
	@Range(min = 1l, message = "Please select positive numbers Only")
	private Integer maxPasswordLength;
	@Range(min = 1l, message = "Please select positive numbers Only")
	private Integer upperCaseletters;
	@Range(min = 1l, message = "Please select positive numbers Only")
	private Integer lowerCaseletters;
	@Range(min = 1l, message = "Please select positive numbers Only")
	private Integer number;
	@Range(min = 1l, message = "Please select positive numbers Only")
	private  Integer specialCharacters;
	private boolean lastPasswordCheck;
	private boolean whiteSpaceCheck;

}
