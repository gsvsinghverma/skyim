package com.adv.serviceimpl;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.adv.model.Admin;
import com.adv.model.AdminRoles;
import com.adv.pagin.GetAllRequest;
import com.adv.payloads.ModuleSubModulePayload;
import com.adv.payloads.apirequests.AdminRoleSaveRequest;
import com.adv.payloads.apirequests.UpdateAdminRoleRequest;
import com.adv.payloads.apiresponse.BasicApiResponse;
import com.adv.repository.AdminRepository;
import com.adv.repository.AdminRolesRepository;
import com.adv.service.AdminRoleService;
import com.adv.util.Constant;
import com.adv.util.Util;

@Service
public class AdminRoleServiceImpl implements AdminRoleService {
	@Autowired
	private AdminRolesRepository adminRoleRepo;

	@Autowired
	private AdminRepository adminrepo;

	@Override
	public BasicApiResponse saveAdminRole(UpdateAdminRoleRequest adminRole) {
		BasicApiResponse response = new BasicApiResponse();
		List<Long> subModuleIds = new ArrayList<Long>();
		List<Long> moduleIds = new ArrayList<Long>();

		try {
			Optional<AdminRoles> adminRolesDetails = adminRoleRepo.findById(adminRole.getId());
			for (ModuleSubModulePayload modulePayload : adminRole.getModuleSubmoduleMappings()) {
				moduleIds.addAll(modulePayload.getModulePayload().getId());

			}
			for (ModuleSubModulePayload modulePayload : adminRole.getModuleSubmoduleMappings()) {
				subModuleIds.addAll(modulePayload.getModulePayload().getSubmodulepayload().getId());

			}

			if (!adminRolesDetails.isEmpty()) {

				AdminRoles adminRoles = adminRolesDetails.get();

				Timestamp currentDT = new Timestamp(System.currentTimeMillis());
				adminRoles.setUpdationDate(currentDT);
				if(!adminRole.getName().equals("")) {
				   adminRoles.setName(adminRole.getName());
				}
				
			}}

	catch (Exception e) {
			e.printStackTrace();
			response.setStatus(false);
			response.setMessage(e.toString());
		}
		return response;
	}

	@Override
	public AdminRoles findByRoleName(String trim) {
		return adminRoleRepo.findByName(trim);

	}

	@Override
	public AdminRoles get(long id) {
		return adminRoleRepo.findById(id).orElse(null);
	}

	@Override
	public List<AdminRoles> getAllAdminRole() {

		return adminRoleRepo.findAll();
	}

	@Override
	public BasicApiResponse deleteAdminRole(long adminRoleId) {
		BasicApiResponse apiResponse = new BasicApiResponse();
		boolean testAdmin=true;
		try {
			AdminRoles admRoles = adminRoleRepo.findById(adminRoleId).get();
		    List<Admin> admDetails= adminrepo.findAll();
		    for(Admin admList:admDetails) {
		    	if(admList.getRole().getId()==adminRoleId) {
		    		testAdmin=false;
		    	}
		    	
		    }
		    
//			
			if (admRoles == null || testAdmin ) {
				apiResponse.setMessage("Admin Not Mapped with given RoleId");
				apiResponse.setStatus(false);
			} else {

				admRoles.setActive(false);
				adminRoleRepo.save(admRoles);
				apiResponse.setStatus(true);
				apiResponse.setMessage("Role Deleted Successfully");

			}
		} catch (Exception e) {
			e.printStackTrace();
			apiResponse.setStatus(true);
			apiResponse.setMessage(e.toString());
			return apiResponse;
		}
		return apiResponse;
	}

	@Override
	public boolean existByModuleId(long id) {

		return false;
	}

	@Override
	public BasicApiResponse saveAdminRole(AdminRoleSaveRequest adminRole) {
		return null;
	}

	@Override
	public Page<AdminRoles> getAllBySort(GetAllRequest pagingRequest) {

		String sortColumn = "creationDate";
		Sort sort = Sort.by(sortColumn).descending();

		Pageable paging = null;

		if (pagingRequest.getSort() != null) {

			if (pagingRequest.getSort().getColumn() != null && pagingRequest.getSort().getDirection() != null) {

				sortColumn = pagingRequest.getSort().getColumn();
				String dir = pagingRequest.getSort().getDirection();

				if (dir.equalsIgnoreCase("ASC")) {
					sort = Sort.by(sortColumn).ascending();
				} else {
					sort = Sort.by(sortColumn).descending();
				}

				paging = PageRequest.of(pagingRequest.getPage(), pagingRequest.getSize(), 
						sort);
			}
		} else {

			paging = PageRequest.of(pagingRequest.getPage(), pagingRequest.getSize(), 
					sort);
		}

		Page<AdminRoles> admins = null;

		if (pagingRequest.getDate() != null) {
			String from="";
			String to ="";
			 from = pagingRequest.getDate().getFrom();
			 to = pagingRequest.getDate().getTo();
			 if(from.equals(Constant.STRING_CONSTANT)) {
				 from="";
				 to="";
			 }
			if (!from.equals("")&&(!to.equals(""))) {
				

				Date fromDate = Util.getSearchDate(from);
				Date toDate = Util.getSearchDate(to);
				

				return adminRoleRepo.findByCreationDateBetween(fromDate, toDate, paging);
			} else {
				admins = adminRoleRepo.findAll(paging);
			}
		} else {
			admins = adminRoleRepo.findAll(paging);
		}

		if (admins != null && !admins.isEmpty() && admins.getContent() != null) {
			return  admins;  
			
		}
		else {
			return  admins;
		}
	}

}
