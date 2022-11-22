package com.adv.service;

import java.util.Date;
import java.util.List;

import javax.mail.MessagingException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import com.adv.exception.CustomException;
import com.adv.model.Admin;
import com.adv.pagin.GetAllRequest;
import com.adv.pagin.Sorting;
import com.adv.payloads.BasicResponsePayload;
import com.adv.payloads.apirequests.CpuUsagePayload;
import com.adv.payloads.apirequests.MemoryUsageDurationRequest;
import com.adv.payloads.apirequests.MemoryUsagePayload;
import com.adv.payloads.apirequests.ResetPasswordPayload;
import com.adv.payloads.apirequests.SaveAdminRequest;
import com.adv.payloads.apirequests.UpdateAdminRequest;
import com.adv.payloads.apirequests.UpdateProfileRequest;
import com.adv.payloads.apiresponse.BasicApiResponse;
import com.adv.payloads.apiresponse.DeshboadCountApiResponse;
import com.adv.payloads.apiresponse.DeshboadCountApiResponseDesignation;
import com.adv.payloads.apiresponse.DeshboadCountApiResponseUnit;
import com.adv.projections.CountProjections;

public interface AdminService {

	Admin saveAdminDetails(String username);

	Admin get(Long attribute);

	BasicApiResponse changePassword(String username, String password, String newPassword);

	List<Admin> getAllAdminData();

	
	boolean isAdminExistByUserIdAndModuleId(Long admin_id, Long module_id);

	boolean deleteAdmin(Admin admin);

	Admin updateAdmin(UpdateAdminRequest admin);


	BasicApiResponse saveAdmin(SaveAdminRequest request);

	Admin loginAdmin(String userName, String password);

	boolean existsByUserName(String trim);

	
	boolean existsByEmail(String trim);

	
	boolean findByUserNameUpdate(String trim, Long id);

	
	Page<Admin> getAllAdmins(GetAllRequest pagingRequest);

	public Page<Admin> getAllAdminsPageData(Pageable pageable);

	byte[] exportExcel(Date dateFrom, Date dateTo) throws CustomException;

	byte[] exportExcelByUsername(String username,  Date dateFrom, Date dateTo)
			throws CustomException;

	BasicResponsePayload updateProfilePhoto(MultipartFile file, String username);

	
	
	BasicApiResponse resetPasswords(ResetPasswordPayload request) throws MessagingException;

	
	public List<Admin> listAll(Sorting sort);
	public Admin getactiveAnddeactive(long id);
	Admin disableoractiveAdmin(Long id);
	


	Admin updateAdminProfile(UpdateProfileRequest request);

	BasicApiResponse resetPassword(String username, String newPassword);

	Admin gets(Long id);

	Admin getactive(Long id);
	
	CountProjections getcountdetails();

	List<MemoryUsagePayload> getmemoryUsagedetails( MemoryUsageDurationRequest value);
	
	List<CpuUsagePayload>  getCpuUsagedetails( MemoryUsageDurationRequest value);
	
	public  void savememoryUsageFirstTime();
	public  void savemecpuUsageFirstTime();
	
	BasicApiResponse getAllDiskDetails();
	
	
	DeshboadCountApiResponse getAllCallhistoryCountbylocation() ;
	DeshboadCountApiResponseUnit getAllCallhistoryCountbyUnit() ;
	DeshboadCountApiResponseDesignation getAllCallhistoryCountbyDesignation() ;
}
