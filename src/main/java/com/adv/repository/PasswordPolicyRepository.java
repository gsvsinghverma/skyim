package com.adv.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.adv.model.PasswordPolicy;

public interface PasswordPolicyRepository extends JpaRepository<PasswordPolicy, Long>{

	 @Query(value="select * FROM passwordpolicy e WHERE e.password_for=0 ",nativeQuery = true)
		public  PasswordPolicy getByPasswordFor();
}
