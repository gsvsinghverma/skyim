package com.adv.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.adv.model.RoleMapping;

public interface RoleMappingRepository extends JpaRepository<RoleMapping, Long>{


	@Query(value= "select * from role_mapping where rolesidpk=?1",nativeQuery=true)
List <RoleMapping> getRoleMappeddata(Long id);
	
	
}
