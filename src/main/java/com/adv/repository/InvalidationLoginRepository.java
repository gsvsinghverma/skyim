package com.adv.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.adv.model.InvalidationLogin;
@Repository
public interface InvalidationLoginRepository extends JpaRepository<InvalidationLogin, Long> {

	InvalidationLogin findByUsernameAndIsvalidate(String username,boolean invalid);
	InvalidationLogin findByUsername(String username);
}
