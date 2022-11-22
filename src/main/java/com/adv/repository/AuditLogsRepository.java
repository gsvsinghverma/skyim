package com.adv.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.adv.model.AuditLog;
public interface AuditLogsRepository extends JpaRepository<AuditLog, Long>{
	Optional<AuditLog> findById(Long id);
	long countByCreationDateBetween(Date from, Date to);
	
	Page<AuditLog> findAll(Pageable pageable);

	List<AuditLog> findByCreationDateBetween(Date from, Date to);
	
	List<AuditLog> findAllByOrderByCreationDateDesc();

	
	@Query("SELECT m FROM AuditLog m WHERE m.userName LIKE %:value%")
	List<AuditLog> getBySearch(String value);
	
	Page<AuditLog> findByCreationDateBetween(Date fromDate, Date toDate, Pageable paging);
	
	@Query(value="select u from AuditLog u where u.userName like %:username%  and u.moduleName like %:modulename%  " )
	Page<AuditLog> findBySearch(@Param("username") String username,@Param("modulename") String modulename, Pageable paging);
	
	

}
