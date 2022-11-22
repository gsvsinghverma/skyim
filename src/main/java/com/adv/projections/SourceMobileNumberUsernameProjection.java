package com.adv.projections;

import org.springframework.beans.factory.annotation.Value;

public interface SourceMobileNumberUsernameProjection {

	@Value("#{target.sourceMobileNumber.username}")
	public String getUsername();

}