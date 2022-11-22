package com.adv.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.adv.model.Admin;

import com.adv.projections.PasswordProjection;

public interface AdminRepository extends JpaRepository<Admin, Long> {

	Admin findByUsername(String username);

	@Query(value = "select * from admin where is_deleted=false or user_name=?1 or password=?2", nativeQuery = true)
	Admin findByUsernameAndPasswordAndisdelete(String userName, String oldEncPass);

	@Query(value = "select * from admin where is_deleted=false and password=?1", nativeQuery = true)
	Admin findBypass(String oldEncPass);

	@Query(value = "select * from admin where is_deleted=false and user_name=?1", nativeQuery = true)
	Admin findusernameandnotdeleted(String username);

	Admin findByUsernameAndPassword(String userName, String password);

	boolean existsByUsername(String username);

	boolean existsByEmail(String email);

	@Query(value = "select adm.admin_role_id from admin ar inner join adm admin_roles on ar.id=adm.admin_role_id where adm.active=1", nativeQuery = true)
	Admin getAdminDetails();

	@Query(value = "select * from admin where id=?1 and active=1", nativeQuery = true)
	Admin findAdminActive(Long id);

	Admin findByUsernameAndIsDeleted(String username, boolean isDeleted);

	@Query(value = "select * from admin where is_deleted=false and ismasteradmin=false and id=?1", nativeQuery = true)
	Admin fetchAdminMasterData(Long id);

	@Query(value = "select * from admin   where  "

			+ " (?1 IS NULL OR ?10 is null or case when 3 = ?10 then first_name LIKE concat('%',?1,'%') when 2 = ?10 then first_name LIKE concat('%',?1)  when 0 = ?10 then first_name NOT LIKE concat('%',?1,'%')    when 1 = ?10 then  first_name LIKE concat(?1,'%')  when 4 = ?10  then  first_name = ?1   else null  end) "
			+ " and (?2 IS NULL OR  ?11 is null or case when 3 = ?11 then last_name LIKE concat('%',?2,'%') when 2 = ?11 then last_name LIKE concat('%',?2)  when 0 = ?11 then last_name NOT LIKE concat('%',?2,'%')    when 1 = ?11 then  last_name LIKE concat(?2,'%') when 4 = ?11  then  last_name = ?2  else null  end)  "
			+ " and (?3 IS NULL OR  ?12 is null or case when 3 = ?12 then user_name LIKE concat('%',?3,'%') when 2 = ?12 then user_name LIKE concat('%',?3)  when 0 = ?12 then user_name NOT LIKE concat('%',?3,'%')    when 1 = ?12 then  user_name LIKE concat(?3,'%') when 4 = ?12  then  user_name = ?3  else null  end)  "
			+ " and (?4 IS NULL OR  ?13 is null or case when 3 = ?13 then email LIKE concat('%',?4,'%') when 2 = ?13 then email LIKE concat('%',?4)  when 0 = ?13 then email NOT LIKE concat('%',?4,'%')    when 1 = ?13 then  email LIKE concat(?4,'%') when 4 = ?13  then  email = ?4 else null  end) "
			+ " and (?5 IS NULL OR  ?14 is null or case when 3 = ?14 then mobile_number LIKE concat('%',?5,'%') when 2 = ?14 then mobile_number LIKE concat('%',?5)  when 0 = ?14 then mobile_number NOT LIKE concat('%',?5,'%')    when 1 = ?14 then  mobile_number LIKE concat(?5,'%') when 4 = ?14  then  mobile_number = ?5  else null  end)  "
			+ " and (?6 IS NULL OR  ?15 is null or case  when 3 = ?15 then location_id LIKE concat('%',?6,'%') when 2 = ?15 then location_id LIKE concat('%',?6)  when 0 = ?15 then location_id NOT LIKE concat('%',?6,'%')    when 1 = ?15 then  location_id LIKE concat(?6,'%') when 4 = ?15  then  location_id = ?6  else null  end)   "
			+ " and (?7 IS NULL OR  ?16 is null or case when 3 = ?16 then designation_id LIKE concat('%',?7,'%') when 2 = ?16 then designation_id LIKE concat('%',?7)  when 0 = ?16 then designation_id NOT LIKE concat('%',?7,'%')    when 1 = ?16 then  designation_id LIKE concat(?7,'%')   when 4 = ?16  then  designation_id = ?7 else null  end) "
			+ " and (?8 IS NULL OR  ?17 is null or case when 3 = ?17 then unit_id LIKE concat('%',?8,'%') when 2 = ?17 then unit_id LIKE concat('%',?8)  when 0 = ?17 then unit_id NOT LIKE concat('%',?8,'%')    when 1 = ?17 then  unit_id LIKE concat(?8,'%')   when 4 = ?17  then  unit_id = ?8 else null  end)  "
			+ " and (?9 IS NULL OR  ?18 is null or case when 3 = ?18 then admin_role_id LIKE concat('%',?9,'%') when 2 = ?18 then admin_role_id LIKE concat('%',?9)  when 0 = ?18 then admin_role_id NOT LIKE concat('%',?9,'%') when 1 = ?18 then  admin_role_id LIKE concat(?9,'%')   when 4 = ?18  then  admin_role_id = ?9 else null  end) and  is_deleted=false and ismasteradmin=false  "

			, countQuery = "select count(*) from admin   where  "

					+ " (?1 IS NULL OR ?10 is null or case when 3 = ?10 then first_name LIKE concat('%',?1,'%') when 2 = ?10 then first_name LIKE concat('%',?1)  when 0 = ?10 then first_name NOT LIKE concat('%',?1,'%')    when 1 = ?10 then  first_name LIKE concat(?1,'%')  when 4 = ?10  then  first_name = ?1   else null  end) "
					+ " and (?2 IS NULL OR  ?11 is null or case when 3 = ?11 then last_name LIKE concat('%',?2,'%') when 2 = ?11 then last_name LIKE concat('%',?2)  when 0 = ?11 then last_name NOT LIKE concat('%',?2,'%')    when 1 = ?11 then  last_name LIKE concat(?2,'%') when 4 = ?11  then  last_name = ?2  else null  end)  "
					+ " and (?3 IS NULL OR  ?12 is null or case when 3 = ?12 then user_name LIKE concat('%',?3,'%') when 2 = ?12 then user_name LIKE concat('%',?3)  when 0 = ?12 then user_name NOT LIKE concat('%',?3,'%')    when 1 = ?12 then  user_name LIKE concat(?3,'%') when 4 = ?12  then  user_name = ?3  else null  end)  "
					+ " and (?4 IS NULL OR  ?13 is null or case when 3 = ?13 then email LIKE concat('%',?4,'%') when 2 = ?13 then email LIKE concat('%',?4)  when 0 = ?13 then email NOT LIKE concat('%',?4,'%')    when 1 = ?13 then  email LIKE concat(?4,'%') when 4 = ?13  then  email = ?4 else null  end) "
					+ " and (?5 IS NULL OR  ?14 is null or case when 3 = ?14 then mobile_number LIKE concat('%',?5,'%') when 2 = ?14 then mobile_number LIKE concat('%',?5)  when 0 = ?14 then mobile_number NOT LIKE concat('%',?5,'%')    when 1 = ?14 then  mobile_number LIKE concat(?5,'%') when 4 = ?14  then  mobile_number = ?5  else null  end)  "
					+ " and (?6 IS NULL OR  ?15 is null or case  when 3 = ?15 then location_id LIKE concat('%',?6,'%') when 2 = ?15 then location_id LIKE concat('%',?6)  when 0 = ?15 then location_id NOT LIKE concat('%',?6,'%')    when 1 = ?15 then  location_id LIKE concat(?6,'%') when 4 = ?15  then  location_id = ?6  else null  end)   "
					+ " and (?7 IS NULL OR  ?16 is null or case when 3 = ?16 then designation_id LIKE concat('%',?7,'%') when 2 = ?16 then designation_id LIKE concat('%',?7)  when 0 = ?16 then designation_id NOT LIKE concat('%',?7,'%')    when 1 = ?16 then  designation_id LIKE concat(?7,'%')   when 4 = ?16  then  designation_id = ?7 else null  end)   "
					+ " and (?8 IS NULL OR  ?17 is null or case when 3 = ?17 then unit_id LIKE concat('%',?8,'%') when 2 = ?17 then unit_id LIKE concat('%',?8)  when 0 = ?17 then unit_id NOT LIKE concat('%',?8,'%') when 1 = ?17 then  unit_id LIKE concat(?8,'%') when 4 = ?17  then  unit_id = ?8 else null  end)  "
					+ " and (?9 IS NULL OR  ?18 is null or case when 3 = ?18 then admin_role_id LIKE concat('%',?9,'%') when 2 = ?18 then admin_role_id LIKE concat('%',?9)  when 0 = ?18 then admin_role_id NOT LIKE concat('%',?9,'%') when 1 = ?18 then  admin_role_id LIKE concat(?9,'%')   when 4 = ?18  then  admin_role_id = ?9 else null  end) and  is_deleted=false and ismasteradmin=false", nativeQuery = true)

	Page<Admin> findByContainsSearch(String firstName, String lastName, String userName, String email,
			String mobileNumber, Long location, Long designation, Long unit, Long roleid, Integer number10,
			Integer number11, Integer number12, Integer number13, Integer number14, Integer number15, Integer number16,
			Integer number17, Integer number18, Pageable paging);

	@Query(value = "select * from admin u  where (:location is null or  u.location_id = :location) and (:designation is null or  u.designation_id= :designation) and "
			+ "(:unit is null or u.unit_id = :unit) and (:firstname is null or  u.first_name like  %:firstname%) and (:lastname is null or  u.last_name like %:lastname%) and "
			+ "(:username is null or u.user_name like %:username%) and (:email is null or u.email like %:email%) and  u.is_deleted=false and u.ismasteradmin=false",

			countQuery = "SELECT count(*) FROM admin u WHERE (:location is null or  u.location_id = :location) and (:designation is null or  u.designation_id= :designation) and "
					+ " (:unit is null or u.unit_id = :unit) and (:firstname is null or  u.first_name like  %:firstname%) and (:lastname is null or  u.last_name like %:lastname%) and "
					+ "(:username is null or u.user_name like %:username%) and (:email is null or u.email like %:email%)  and u.is_deleted=false and u.ismasteradmin=false", nativeQuery = true)

	Page<Admin> findBySearch(@Param("firstname") String firstName, @Param("lastname") String lastName,
			@Param("username") String userName, @Param("email") String email, Long location, Long designation,
			Long unit, Pageable paging); 

	PasswordProjection findByUsernameAndActive(String username, boolean b);

	List<Admin> findAllByIsDeletedOrderByCreationDateDesc(boolean isDeleted);

	@Query(value = "select e FROM Admin e WHERE e.isDeleted = FALSE")
	List<Admin> getAllAdminIsNotDeleted();

	public List<Admin> findByFirstNameContainsOrLastNameContains(String firstName, String lastName);

	Page<Admin> findByCreationDateBetween(Date fromDate, Date toDate, Pageable paging);

	@Query(value = "select * from admin a where a.ismasteradmin=false and a.is_deleted=false", nativeQuery = true)
	List<Admin> getAdminNonMasterDetails();

	@Query(value = "select a from Admin a where a.ismasteradmin=false and a.isDeleted=false")
	List<Admin> getAdminMasterDetails(Sort ascending);

	Admin save(String newPassword);

	Page<Admin> findByCreationDateBetweenAndIsmasteradminIsAndActive(Date fromDate, Date toDate, boolean ismasteradmin,
			boolean active, Pageable paging);

	@Query(value = "select * from admin  where ismasteradmin = ?1  and is_deleted=false", countQuery = "SELECT count(*) FROM admin WHERE ismasteradmin = ?1 and is_deleted=false", nativeQuery = true)
	Page<Admin> findBySearchNonMaster(boolean b, Pageable paging);

}
