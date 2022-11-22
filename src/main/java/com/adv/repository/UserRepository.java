package com.adv.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.adv.model.Admin;
import com.adv.model.User;
import com.adv.projections.DisplayNameProjection;
import com.adv.projections.OutboundcidProjection;
import com.adv.projections.PasswordProjection;
import com.adv.projections.ProjectionContacts;

public interface UserRepository extends JpaRepository<User, Long> {

	User findByUsernameAndPassword(String username, String password);

	User findByUsername(String username);

	User findByEmailIdAndPassword(String email, String password);

	User findByEmailId(String username);

	User findByGoogleId(String socialId);

	User findByFacebookId(String socialId);

	User findByVirtualNumber(String number);

	long countByActive(boolean active);

	List<User> findByActive(boolean status);

	Page<User> findByActive(boolean status, Pageable paging);

	DisplayNameProjection findDisplayNameByUsername(String username);

	OutboundcidProjection findOutboundCidByUsername(String username);

	User findByLoginUsernameAndPassword(String loginUsername, String password);

	User findByLoginUsername(String loginUsername);

	Page<User> findByActiveAndDisplayNameContainsOrActiveAndFirstNameContainsOrActiveAndLastNameContainsOrActiveAndEmailIdContains(
			boolean active, String username, boolean active2, String firstName, boolean active3, String lastName,
			boolean active4, String emailId, Pageable paging);

	long countByActiveAndDisplayNameContainsOrActiveAndFirstNameContainsOrActiveAndLastNameContainsOrActiveAndEmailIdContains(
			boolean active, String username, boolean active2, String firstName, boolean active3, String lastName,
			boolean active4, String emailId);

	Set<User> findByActiveAndUsernameIn(boolean status, Set<String> username);

	List<User> findByActiveAndIdIn(boolean status, Set<Long> ids);

	boolean existsByUsername(String username);

	boolean existsByEmailId(String username);

	boolean existsByLoginUsername(String loginUsername);

	PasswordProjection findByLoginUsernameAndActive(String loginUsername, boolean status);

	List<ProjectionContacts> findByActiveAndUsernameNot(boolean active, String username);
	List<Admin> findAllByOrderByCreationDateDesc();
	long countByIsDeleted(boolean del);
	
	
	@Query(value="select * from user  where location_id=?1 and is_deleted=false ",nativeQuery = true)
	List<User> getUserbylocationId(Long locationid);
	
	
	@Query(value="select * from user  where unit_id=?1 and is_deleted=false ",nativeQuery = true)
	List<User> getUserbyUnitId(Long unitid);
	
	
	@Query(value="select * from user  where designation_id=?1  and is_deleted=false ",nativeQuery = true)
	List<User> getUserbyDesignationId(Long designationid);
	
	
	
}
