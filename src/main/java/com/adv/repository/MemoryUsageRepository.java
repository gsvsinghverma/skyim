package com.adv.repository;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.adv.model.MemoryUsage;

@Repository
public interface MemoryUsageRepository extends JpaRepository<MemoryUsage, Long> {

	
	@Modifying
    @Transactional
    @Query(value="DELETE FROM memoryusage m WHERE m.creation_date < ?1",nativeQuery = true)
    int removeOlderThan(@Param("date")Timestamp date);

	 @Query(value="select * FROM memoryusage m WHERE m.creation_date > ?1",nativeQuery = true)
	List<MemoryUsage> findByCreationdate(Timestamp newdate);
	

}
