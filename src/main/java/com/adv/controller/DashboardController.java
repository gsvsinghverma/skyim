package com.adv.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.adv.configuration.JwtTokenUtil;
import com.adv.model.Admin;
import com.adv.payloads.EmptyJsonBody;
import com.adv.payloads.apirequests.CpuUsagePayload;
import com.adv.payloads.apirequests.MemoryUsageDurationRequest;
import com.adv.payloads.apirequests.MemoryUsagePayload;
import com.adv.payloads.apiresponse.BasicApiResponse;
import com.adv.payloads.apiresponse.BasicDashboardApiResponse;
import com.adv.payloads.apiresponse.DeshboadCountApiResponse;
import com.adv.payloads.apiresponse.DeshboadCountApiResponseDesignation;
import com.adv.payloads.apiresponse.DeshboadCountApiResponseUnit;
import com.adv.projections.CountProjections;
import com.adv.repository.AdminRepository;
import com.adv.repository.InvalidationLoginRepository;
import com.adv.repository.PasswordPolicyRepository;
import com.adv.repository.RoleMappingRepository;
import com.adv.repository.UserTokenRepo;
import com.adv.service.AdminService;
import com.adv.service.AuditLogsService;
import com.adv.service.ModulesService;
import com.adv.serviceimpl.UserDetailsServiceImpl;
import com.adv.util.Constant;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
@RestController
@RequestMapping("/dashboard")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@SecurityRequirement(name = "Bearer Authentication")
public class DashboardController {

	@Autowired
	InvalidationLoginRepository invalidRepo;
	@Autowired
	AdminRepository adminRepo;

	@Autowired
	AuditLogsService auditLogService;

    @Autowired	
	RoleMappingRepository rolemappingRepo;
	
	@Autowired
	AdminService adminService;

	@Autowired
	ModulesService modulesService;

	@Autowired
	UserDetailsServiceImpl adminJwtService;
	
    @Autowired
	UserTokenRepo usertokenRepo;

	@Autowired
	JwtTokenUtil jwtTokenUtil;

	@Autowired
	PasswordPolicyRepository passwordPolicyRepository;


	
	
	@Hidden
	@PostMapping(path = "/getSuperAdminAuth")
	public ResponseEntity<Admin> getSuperAdminAuth() throws Exception {


		return ResponseEntity.ok(adminService.get(1l));
	}

	@PostMapping(value = "/getAllDashboardDetails")
	public ResponseEntity<BasicDashboardApiResponse> getAllDashboardDetails() {

		BasicDashboardApiResponse response = new BasicDashboardApiResponse();
		response.setData(new EmptyJsonBody());
		response.setStatus(false);

		try {
			
			CountProjections locations = adminService.getcountdetails();

		
				response.setStatus(true);
				response.setMessage("Details Fetched Successfully!");
				response.setData(locations);
				
		} catch (Exception e) {
			e.printStackTrace();
			response.setMessage(Constant.ERROR_API_RESPONSE);
		}

		return ResponseEntity.ok(response);
	}
	
	
	
	
	
	
	@PostMapping(value = "/getAllMemoryUsageDetails")
	public ResponseEntity<BasicDashboardApiResponse> getAllMemoryUsageDetails(@RequestBody MemoryUsageDurationRequest request) {

		BasicDashboardApiResponse response = new BasicDashboardApiResponse();
		response.setData(new EmptyJsonBody());
		response.setStatus(false);

		try {
			
			List<MemoryUsagePayload> payload = adminService.getmemoryUsagedetails(request);

		
				response.setStatus(true);
				response.setMessage("Details Fetched Successfully!");
				response.setData(payload);
			
			
//			else {
//				response.setMessage("Details Fetched Failed!");
//			}

		} catch (Exception e) {
			e.printStackTrace();
			
			response.setMessage(Constant.ERROR_API_RESPONSE);
		}

		return ResponseEntity.ok(response);
	}
	
	
	@PostMapping(value = "/getAllDiskDetails")
    public ResponseEntity<BasicDashboardApiResponse> getAllDiskDetails() {



       BasicDashboardApiResponse response = new BasicDashboardApiResponse();
        response.setData(new EmptyJsonBody());
        response.setStatus(false);



       try {
             BasicApiResponse allDiskDetails = adminService.getAllDiskDetails();

                response.setStatus(true);
                response.setData(allDiskDetails.getData());
       } catch (Exception e) {
            e.printStackTrace();
            response.setMessage(Constant.ERROR_API_RESPONSE);
        }



       return ResponseEntity.ok(response);
    }
	
	
	
	
	@PostMapping(value = "/getAllCpuUsageDetails")
	public ResponseEntity<BasicDashboardApiResponse> getAllCpuUsageDetails(@RequestBody MemoryUsageDurationRequest request) {

		BasicDashboardApiResponse response = new BasicDashboardApiResponse();
		response.setData(new EmptyJsonBody());
		response.setStatus(false);

		try {
			
			List<CpuUsagePayload> payload = adminService.getCpuUsagedetails(request);

		
				response.setStatus(true);
				response.setMessage("Details Fetched Successfully!");
				response.setData(payload);
			
			
//			else {
//				response.setMessage("Details Fetched Failed!");
//			}

		} catch (Exception e) {
			e.printStackTrace();
		
			response.setMessage(Constant.ERROR_API_RESPONSE);
		}

		return ResponseEntity.ok(response);
	}
	
	
	
	
	
	
	@PostMapping(value = "/getAllCallCountByLocation")
	public ResponseEntity<BasicDashboardApiResponse> getAllCallhistoryCountbylocation() {

		BasicDashboardApiResponse response = new BasicDashboardApiResponse();
		response.setData(new EmptyJsonBody());
		response.setStatus(false);

		try {
			
			DeshboadCountApiResponse allCallhistoryCountbylocation = adminService.getAllCallhistoryCountbylocation();

		if(allCallhistoryCountbylocation!=null) {
				response.setStatus(true);
				response.setMessage("Details Fetched Successfully!");
				response.setData(allCallhistoryCountbylocation);
			
		}
		else {
			response.setStatus(false);
			response.setMessage("Details  Not Fetched Successfully!");
			
		}

		} catch (Exception e) {
			e.printStackTrace();
			response.setMessage(Constant.ERROR_API_RESPONSE);
		}

		return ResponseEntity.ok(response);
	}
	
	
	
	
	
	@PostMapping(value = "/getAllCallCountByUnit")
	public ResponseEntity<BasicDashboardApiResponse> getAllCallCountbyUnit() {

		BasicDashboardApiResponse response = new BasicDashboardApiResponse();
		response.setData(new EmptyJsonBody());
		response.setStatus(false);

		try {
			
			DeshboadCountApiResponseUnit allCallhistoryCountbylocation = adminService.getAllCallhistoryCountbyUnit();

		if(allCallhistoryCountbylocation!=null) {
				response.setStatus(true);
				response.setMessage("Details Fetched Successfully!");
				response.setData(allCallhistoryCountbylocation);
			
		}
		else {
			response.setStatus(false);
			response.setMessage("Details  Not Fetched Successfully!");
			
		}

		} catch (Exception e) {
			e.printStackTrace();
			response.setMessage(Constant.ERROR_API_RESPONSE);
		}

		return ResponseEntity.ok(response);
	}
	
	
	
	
	@PostMapping(value = "/getAllCallCountByDesignation")
	public ResponseEntity<BasicDashboardApiResponse> getAllCallhistoryCountbyDesignation() {

		BasicDashboardApiResponse response = new BasicDashboardApiResponse();
		response.setData(new EmptyJsonBody());
		response.setStatus(false);

		try {
			
			DeshboadCountApiResponseDesignation allCallhistoryCountbylocation = adminService.getAllCallhistoryCountbyDesignation();

		if(allCallhistoryCountbylocation!=null) {
				response.setStatus(true);
				response.setMessage("Details Fetched Successfully!");
				response.setData(allCallhistoryCountbylocation);
			
		}
		else {
			response.setStatus(false);
			response.setMessage("Details  Not Fetched Successfully!");
			
		}

		} catch (Exception e) {
			e.printStackTrace();
			response.setMessage(Constant.ERROR_API_RESPONSE);
		}

		return ResponseEntity.ok(response);
	}
	
	
	
	
	

}