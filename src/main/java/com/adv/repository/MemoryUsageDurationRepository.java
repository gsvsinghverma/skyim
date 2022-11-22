package com.adv.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.adv.model.MemoryUsageDuration;

@Repository
public interface MemoryUsageDurationRepository extends JpaRepository<MemoryUsageDuration, Long> {

	
	
	
	@Modifying
	@Transactional
	@Query(value = " insert "
			+ "    into "
			+ "       memoryusageduration "
			+ "        (id, duration_type) "
			+ "    VALUES "
			+ "        (1,'last 7 days '),("
			+ "            2,'last 15 days'"
			+ "        ),("
			+ "            3,'last 24 days' "
			+ "        ),( "
			+ "            4,'last 30 days' "
			+ "        ),( "
			+ "            5,'last 1 hour' "
			+ "        ),( "
			+ "            6,'last 7 hour'"
			+ "        );", nativeQuery = true)
	public void insertMemoryUsageDuration();
	
	
	
	
	
	

}
