package com.adv.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.adv.model.Admin;
import com.adv.model.GroupManagement;

public interface GroupManagementRepository extends JpaRepository<GroupManagement,Long> {

	List<Admin> findAllByOrderByCreationDateDesc();
	long countByIsDeleted(boolean del);

}
