package com.adv.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.adv.model.UserToken;

public interface UserTokenRepo extends JpaRepository<UserToken, Long> {

	UserToken findByToken(String token);
	
}
