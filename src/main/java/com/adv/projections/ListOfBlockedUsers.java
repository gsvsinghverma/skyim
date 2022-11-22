package com.adv.projections;

import org.springframework.beans.factory.annotation.Value;

public interface ListOfBlockedUsers {

	@Value("#{target.destinationMobileNumber.displayName}")
	String getDisplayName();

	@Value("#{target.destinationMobileNumber.firstName}")
	String getFirstName();

	@Value("#{target.destinationMobileNumber.lastName}")
	String getLastName();

	@Value("#{target.destinationMobileNumber.username}")
	String getUsername();

	@Value("#{target.destinationMobileNumber.mobileNumber}")
	String getMobileNumber();

	@Value("#{target.destinationMobileNumber.emailId}")
	String getEmailId();

	@Value("#{target.destinationMobileNumber.profilePhoto}")
	String getProfilePhoto();

}