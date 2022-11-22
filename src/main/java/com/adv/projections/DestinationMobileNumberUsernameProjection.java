package com.adv.projections;

import org.springframework.beans.factory.annotation.Value;

public interface DestinationMobileNumberUsernameProjection {

	@Value("#{target.destinationMobileNumber.username}")
	public String getUsername();

}