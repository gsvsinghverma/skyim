package com.adv.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.adv.model.AdminRoles;
import com.adv.pagin.GetAllRequest;
import com.adv.payloads.apirequests.AdminRoleSaveRequest;
import com.adv.payloads.apirequests.UpdateAdminRoleRequest;
import com.adv.payloads.apiresponse.BasicApiResponse;

public interface AdminRoleService {
	
	boolean existByModuleId(long id);

	BasicApiResponse saveAdminRole(AdminRoleSaveRequest adminRole);
	AdminRoles get(long id);
	
	List<AdminRoles> getAllAdminRole();
	Page<AdminRoles> getAllBySort(GetAllRequest pagingRequest);

	
	BasicApiResponse deleteAdminRole(long adminRoleId);

	AdminRoles findByRoleName(String trim);

	BasicApiResponse saveAdminRole(UpdateAdminRoleRequest adminRole);

}
