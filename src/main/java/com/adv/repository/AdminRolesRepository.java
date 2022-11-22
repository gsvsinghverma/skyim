package com.adv.repository;

import java.util.Date;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.adv.model.AdminRoles;

public interface AdminRolesRepository extends JpaRepository<AdminRoles, Long> {

	@Query(value = "select * from modules where id=?1", nativeQuery = true)
	AdminRoles getModulesById(long id);

	Page<AdminRoles> findByCreationDateBetween(Date fromDate, Date toDate, Pageable paging);

	@Query(value = "select * from admin_roles where id=?1 and ACTIVE = true", nativeQuery = true)
	AdminRoles findRole(Long id);

	AdminRoles findByName(String trim);

}
