package com.adv.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.adv.model.OtpDetails;

public interface OtpDetailsRepository extends JpaRepository<OtpDetails, Long> {

	OtpDetails findByUserId(Long id);
	
	OtpDetails findByUserUsernameAndOtp(String username, String Otp);
	
	OtpDetails findByUserUsername(String username);
	
	OtpDetails findByUserEmailIdAndOtp(String username, String Otp);
}
