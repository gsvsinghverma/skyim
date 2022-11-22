package com.adv.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.adv.configuration.JwtTokenUtil;
import com.adv.exception.CustomException;
import com.adv.model.Admin;
import com.adv.model.PasswordPolicy;
import com.adv.model.RoleMapping;
import com.adv.model.UserToken;
import com.adv.pagin.GetAllRequest;
import com.adv.payloads.EmptyJsonBody;
import com.adv.payloads.apirequests.AdminLoginRequest;
import com.adv.payloads.apirequests.AdminResetPasswordRequest;
import com.adv.payloads.apirequests.AdminlogoutUserRequest;
import com.adv.payloads.apirequests.ResetPasswordPayload;
import com.adv.payloads.apirequests.SaveAdminRequest;
import com.adv.payloads.apirequests.UpdateAdminRequest;
import com.adv.payloads.apirequests.UpdateProfileRequest;
import com.adv.payloads.apirequests.UsernameAndPasswordRequest;
import com.adv.payloads.apiresponse.AdminApiResponse;
import com.adv.payloads.apiresponse.AdminResponse;
import com.adv.payloads.apiresponse.BasicApiResponse;
import com.adv.payloads.apiresponse.BasicDashboardApiResponse;
import com.adv.payloads.videomeeting.IdRequest;
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
import com.adv.util.AdminPDFExporter;
import com.adv.util.Constant;
import com.adv.util.Util;
import com.lowagie.text.DocumentException;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/admin")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@SecurityRequirement(name = "Bearer Authentication")
public class AdminController {

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

	@PostMapping(value = "/sendMailForForgetPassword")
	public ResponseEntity<BasicApiResponse> sendMailForResetPassword(@RequestBody ResetPasswordPayload request) {
		BasicApiResponse response = new BasicApiResponse();
		try {
			if (request.getUsername() == null || request.getUsername().isBlank()) {
				response.setMessage("Invalid User Name!");
				response.setHttpstatus(HttpStatus.OK);
				return ResponseEntity.ok(response);
			}

			if (!adminService.existsByUserName(request.getUsername().trim())) {
				response.setMessage("UserName Not Valid !");
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
			}

			response = adminService.resetPasswords(request);
			if (response != null) {
				return ResponseEntity.status(HttpStatus.OK).body(response);
			} else {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);

		}
	}

	@PostMapping(value = "/getAllAdmins")
	public ResponseEntity<BasicApiResponse> getAllAdmins() {

		BasicApiResponse response = new BasicApiResponse();
		response.setData(new EmptyJsonBody());
		response.setStatus(false);

		try {
			List<Admin> admins = adminService.getAllAdminData();

			if (admins != null && !admins.isEmpty()) {
				response.setData(admins);

				response.setStatus(true);
				response.setHttpstatus(HttpStatus.OK);
				response.setMessage("Admins Fetched Successffully!");

				auditLogService.addAuditLog(Constant.ADMINMODULENAME, Constant.ADMINGETALLADMINSDES,
						Constant.USERNAMEFROMTOKEN, Constant.ADMINGETALLADMINSSTATUS, Constant.TYPE);
				return ResponseEntity.status(HttpStatus.OK).body(response);

			} else {
				response.setStatus(false);
				response.setHttpstatus(HttpStatus.BAD_REQUEST);

				response.setMessage("Admins Not Found!");
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
			}
		} catch (Exception e) {
			response.setStatus(false);
			response.setHttpstatus(HttpStatus.BAD_REQUEST);
			response.setMessage(Constant.ERROR_API_RESPONSE);
		}

		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	@PostMapping(value = "/getAll")
	public ResponseEntity<BasicApiResponse> getAllAdmins(@RequestBody GetAllRequest pagingRequest) {

		BasicApiResponse response = new BasicApiResponse();
		response.setData(new EmptyJsonBody());
		response.setStatus(false);

		try {
			Page<Admin> admins = adminService.getAllAdmins(pagingRequest);

			if (admins != null && !admins.isEmpty()) {
				response.setData(admins);

				response.setStatus(true);
				response.setMessage("Admin Fetched Successffully!");

				auditLogService.addAuditLog(Constant.ADMINGETALL, Constant.ADMINGETALLDES, Constant.USERNAMEFROMTOKEN,
						Constant.ADMINGETALLSTATUS, Constant.TYPE);

			} else {

				response.setMessage("Admin Not Found !");
				return ResponseEntity.status(HttpStatus.OK).body(response);

			}
		} catch (Exception e) {
			e.printStackTrace();

			response.setMessage(Constant.ERROR_API_RESPONSE);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);

		}
		return ResponseEntity.ok(response);
	}

	@PostMapping(value = "/login")
	public ResponseEntity<BasicApiResponse> login(@RequestBody AdminLoginRequest request) {


		BasicApiResponse response = new BasicApiResponse();
		response.setStatus(false);
		response.setData(new EmptyJsonBody());

		try {
			if (request.getUsername() == null || request.getUsername().isBlank()) {
				response.setMessage("Invalid Username!");
				return ResponseEntity.ok(response);
			}
			if (request.getPassword() == null || request.getPassword().isBlank()) {
				response.setMessage("Invalid Password!");
				return ResponseEntity.ok(response);
			}

			String userName = request.getUsername();
			String password = request.getPassword();
			password = DigestUtils.md5Hex(password);

			Admin admin = adminService.loginAdmin(userName, password);

			if (admin != null) {
				AdminResponse adminresponse = new AdminResponse();
				adminresponse.setId(admin.getId());
				adminresponse.setUsername(admin.getUsername());
				adminresponse.setFirstName(admin.getFirstName());
				adminresponse.setLastName(admin.getLastName());
				adminresponse.setCreationDate(admin.getCreationDate());
				adminresponse.setUpdationDate(admin.getUpdationDate());
				adminresponse.setActive(admin.isActive());
				adminresponse.setDeleted(admin.isDeleted());
				adminresponse.setProfilePhoto(admin.getProfilePhoto());
				adminresponse.setMobileNumber(admin.getMobileNumber());
				adminresponse.setEmail(admin.getEmail());

				adminresponse.setDesignation(admin.getDesignation());
				adminresponse.setLocation(admin.getLocation());
				adminresponse.setUnit(admin.getUnit());

				List<RoleMapping> roleMappeddata = rolemappingRepo.getRoleMappeddata(admin.getRole().getId());

				adminresponse.setRole(roleMappeddata);

				PasswordPolicy passwordPolicy = passwordPolicyRepository.getByPasswordFor();
				Timestamp validTill = passwordPolicy.getValidTill();

				Timestamp creationDate = new Timestamp(System.currentTimeMillis());
				long milliseconds1 = creationDate.getTime();
				long milliseconds2 = validTill.getTime();
				int days1 = (int) (milliseconds1 / (1000 * 60 * 60 * 24));
				int days2 = (int) (milliseconds2 / (1000 * 60 * 60 * 24));
				int diffDate = days2 - days1;

				if (diffDate >= 0) {

					response.setPasswordPolicyMessage("Your password policy is going to be expired in " + diffDate
							+ "  days . Kindly update it as required");
				} else {
					response.setPasswordPolicyMessage("Your password policy has expired Kindly update");
				}

				if (admin.isIsmasteradmin() == true) {
					Constant.MASTERFLAG = true;
				} else {
					Constant.MASTERFLAG = false;
				}

				final UserDetails userDetails = adminJwtService.loadUserByAdminname(userName);

				final String tokenString = jwtTokenUtil.generateToken(userDetails);

				UserToken usertoken = new UserToken();

				usertoken.setToken(tokenString);
				Timestamp currentDT = new Timestamp(System.currentTimeMillis());
				usertoken.setCreationDate(currentDT);
				usertoken.setUserName(userDetails.getUsername());
				Constant.USERNAMEFROMTOKEN = userDetails.getUsername();
				usertoken.setDevicetype(Constant.DEVICETYPE);
				usertokenRepo.save(usertoken);

				auditLogService.addAuditLog(Constant.ADMINLOGIN, Constant.ADMINLOGINDES, Constant.USERNAMEFROMTOKEN,
						Constant.ADMINLOGINSTATUS, Constant.TYPE);

				response.setToken(tokenString);

				response.setStatus(true);
				response.setMessage("Login Successfully!");
				response.setData(adminresponse);

				return ResponseEntity.ok(response);

			} else {
				response.setMessage("Invalid Username or Password Provided!");

			}

		} catch (Exception e) {
			e.printStackTrace();
			response.setMessage(Constant.ERROR_API_RESPONSE);

		}
		return ResponseEntity.ok(response);
	}

	@PostMapping(value = "/changePasswordAdminProfile")
	public ResponseEntity<BasicApiResponse> changePassword(@RequestBody UsernameAndPasswordRequest request) {

		BasicApiResponse response = new BasicApiResponse();
		response.setData(new EmptyJsonBody());
		response.setStatus(false);

		try {

			if (request.getUsername() == null || request.getUsername().isBlank()) {
				response.setMessage("Invalid Username!");
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
			}
			if (request.getPassword() == null || request.getPassword().isBlank()) {
				response.setMessage("Invalid Old Password!");
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
			}
			if (request.getNewPassword() == null || request.getNewPassword().isBlank()) {
				response.setMessage("New Password Cannot Be Blank!");
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
			}
			String username = Util.getTrimParam(request.getUsername());
			String newPassword = Util.getTrimParam(request.getNewPassword());
			String oldPassword = Util.getTrimParam(request.getPassword());

			BasicApiResponse adminResponse = adminService.changePassword(username, oldPassword, newPassword);
			if (adminResponse.isStatus() == true) {
				response.setStatus(true);
				response.setMessage(adminResponse.getMessage());

				auditLogService.addAuditLog(Constant.ADMINCHANGEPASSWORD, Constant.ADMINCHANGEPASSWORDDESCRIPTION,
						Constant.USERNAMEFROMTOKEN, Constant.ADMINCHANGEPASSWORDSTATUS, Constant.TYPE);

			} else {
				response.setMessage(adminResponse.getMessage());

			}
		} catch (Exception e) {
			e.printStackTrace();
			response.setMessage(Constant.ERROR_API_RESPONSE);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);

		}
		return ResponseEntity.ok(response);

	}

	@PostMapping("/resetPassword")
	public ResponseEntity<BasicApiResponse> resetPassword(@RequestBody AdminResetPasswordRequest request) {

		BasicApiResponse response = new BasicApiResponse();
		response.setData(new EmptyJsonBody());
		response.setStatus(false);

		try {

			Admin admin = adminRepo.findByUsernameAndIsDeleted(request.getUsername(), false);

			if (admin == null) {
				response.setMessage("Admin Not Found !!");
				return ResponseEntity.status(HttpStatus.OK).body(response);

			}

			if (request.getUsername() == null || request.getUsername().isBlank()) {
				response.setMessage("Invalid UserName!");
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
			}
			if (request.getNewPassword() == null || request.getNewPassword().isBlank()) {
				response.setMessage("New Password Cannot Be Blank!");
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
			}

			String username = Util.getTrimParam(request.getUsername());
			String newPassword = Util.getTrimParam(request.getNewPassword());

			BasicApiResponse adminResponse = adminService.resetPassword(username, newPassword);
			if (adminResponse.isStatus() == true) {
				response.setStatus(true);
				response.setMessage(adminResponse.getMessage());

				auditLogService.addAuditLog(Constant.ADMINRESETPASSWORD, Constant.ADMINRESETPASSWORDDESCRIPTION,
						Constant.USERNAMEFROMTOKEN, Constant.ADMINRESETPASSWORDSTATUS, Constant.TYPE);

			} else {
				response.setMessage(adminResponse.getMessage());
				return ResponseEntity.status(HttpStatus.OK).body(response);
			}

		} catch (Exception e) {
			e.printStackTrace();
			response.setMessage(Constant.ERROR_API_RESPONSE);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);

		}
		return ResponseEntity.ok(response);

	}

	@PostMapping(value = "/logout")
	public ResponseEntity<BasicApiResponse> logoutUser(@RequestBody AdminlogoutUserRequest request) {

		BasicApiResponse response = new BasicApiResponse();
		response.setData(new EmptyJsonBody());
		response.setStatus(false);

		try {
			String username = request.getUsername();

			if (username != null) {

				auditLogService.addAuditLog(Constant.ADMINLOGOUT, Constant.ADMINLOGOUTDES, Constant.USERNAMEFROMTOKEN,
						Constant.ADMINLOGOUTSTATUS, Constant.TYPE);

				response.setStatus(true);
				response.setMessage("Logout Successfully!");
			}
		} catch (Exception e) {
			e.printStackTrace();

			response.setMessage(Constant.ERROR_API_RESPONSE);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);

		}
		return ResponseEntity.ok(response);
	}

	@PostMapping(value = "/save")
	public ResponseEntity<BasicApiResponse> saveadmin(@RequestBody SaveAdminRequest request) {

		BasicApiResponse response = new BasicApiResponse();
		response.setData(new EmptyJsonBody());
		response.setStatus(false);
		try {
			if (request.getUsername() == null || request.getUsername().isBlank()) {
				response.setMessage("Invalid Username!");
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
			}
			if (request.getPassword() == null || request.getPassword().isBlank()) {
				response.setMessage("Invalid Password!");
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
			}
			if (request.getRole() == null) {
				response.setMessage("Invalid Role!");
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
			}

			if (adminService.existsByUserName(request.getUsername().trim())) {
				response.setMessage("Username Already Exist!");
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
			}

			if (adminService.existsByEmail(request.getEmail().trim())) {
				response.setMessage("Email Already Exist!");
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
			}

			if (request.getEmail() != null) {
				String regex = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
				Pattern pattern = Pattern.compile(regex);
				boolean b = request.getEmail().matches(pattern.toString());
				if (!b) {
					response.setMessage("Email Not Valid Please Provide Valid Email !");

					return ResponseEntity.ok(response);

				}

			}

			BasicApiResponse add_admin = adminService.saveAdmin(request);

			if (add_admin.isStatus() == true) {
				response.setStatus(true);
				response.setMessage(add_admin.getMessage());
				response.setData(add_admin.getData());

				auditLogService.addAuditLog(Constant.ADDUSER, Constant.ADDUSERDES, Constant.USERNAMEFROMTOKEN,
						Constant.ADMINSAVESTATUS, Constant.TYPE);

			}

			else {

				response.setMessage(add_admin.getMessage());
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.setMessage(Constant.ERROR_API_RESPONSE);

			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}

		return ResponseEntity.ok(response);
	}

	@PostMapping(value = "/viewById")
	public ResponseEntity<BasicApiResponse> viewById(@RequestBody IdRequest request) {

		BasicApiResponse response = new BasicApiResponse();
		response.setData(new EmptyJsonBody());
		response.setStatus(false);
		AdminApiResponse adminresp = new AdminApiResponse();

		if (request.getId() == null || request.getId() == 0) {
			response.setMessage("ID can not be null or zero!");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}

		try {
			Admin admin = adminService.get(request.getId());
			if (admin != null) {
				adminresp.setUsername(admin.getUsername());
				adminresp.setFirstName(admin.getFirstName());
				adminresp.setLastName(admin.getLastName());
				adminresp.setActive(admin.isActive());
				adminresp.setProfilePhoto(admin.getProfilePhoto());
				adminresp.setMobileNumber(admin.getMobileNumber());
				adminresp.setEmail(admin.getEmail());
				adminresp.setDesignation(admin.getDesignation());
				adminresp.setLocation(admin.getLocation());
				adminresp.setUnit(admin.getUnit());
				List<RoleMapping> fetchMapDetails = rolemappingRepo.getRoleMappeddata(admin.getRole().getId());
				adminresp.setRolemappingdetails(fetchMapDetails);
				adminresp.setIsmasteradmin(admin.isIsmasteradmin());
				response.setStatus(true);
				response.setMessage("Admin Found Successfully!");
				response.setData(admin);
				response.setHttpstatus(HttpStatus.OK);

				auditLogService.addAuditLog(Constant.ADMINVIEWBYID, Constant.ADDVIEWDES, Constant.USERNAMEFROMTOKEN,
						Constant.ADMINVIEWIDSTATUS, Constant.TYPE);

			} else {
				response.setMessage("Admin Not Found!");
				return ResponseEntity.status(HttpStatus.OK).body(response);
			}
		} catch (Exception e) {
			e.printStackTrace();

			response.setMessage(Constant.ERROR_API_RESPONSE);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}
		return ResponseEntity.ok(response);
	}

	@PostMapping(value = "/update")
	public ResponseEntity<BasicApiResponse> updateAdmin(@RequestBody UpdateAdminRequest request) {
		BasicApiResponse response = new BasicApiResponse();
		response.setData(new EmptyJsonBody());
		response.setStatus(false);
		Admin adminNoMasterDetails = adminService.get(request.getId());
		if (adminNoMasterDetails == null) {
			response.setMessage("Admin Not Found !!");

			return ResponseEntity.status(HttpStatus.OK).body(response);

		}

		if (adminNoMasterDetails.isIsmasteradmin() == true) {
			response.setMessage("Not Authorized to perform these Operations!");
			response.setStatus(false);
			response.setHttpstatus(HttpStatus.BAD_REQUEST);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}

		if (request.getUsername() == null || request.getUsername().isBlank()) {
			response.setMessage("Invalid Username!");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}
		if (request.getRole() == null) {
			response.setMessage("Invalid Role!");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}

		if (request.getId() == null || request.getId() == 0) {
			response.setMessage("ID can not be null or zero!");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}

		try {
			Admin up_admin = adminService.updateAdmin(request);
			if (up_admin != null) {
				response.setStatus(true);
				response.setMessage("Admin Updated Successfully!");
				response.setData(up_admin);

				auditLogService.addAuditLog(Constant.UPDATEADMINS, Constant.UPDATEADMINDESC,
						Constant.USERNAMEFROMTOKEN, Constant.ADMINUPDATES, Constant.TYPE);

			} else {
				response.setMessage("Admin Not Found!");
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
			}
		} catch (Exception e) {
			e.printStackTrace();

			response.setMessage(Constant.ERROR_API_RESPONSE);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);

		}
		return ResponseEntity.ok(response);

	}

	@DeleteMapping(value = "/delete")
	public ResponseEntity<BasicApiResponse> deleteSAMUser(@RequestBody IdRequest request) {
		BasicApiResponse response = new BasicApiResponse();
		response.setData(new EmptyJsonBody());
		response.setStatus(false);

		Admin adminNoMasterDetails = adminService.gets(request.getId());

		if (adminNoMasterDetails != null) {
			if (adminNoMasterDetails.isIsmasteradmin() == true) {
				response.setMessage("Not Authorized to perform these Operations!");
				response.setStatus(false);
				response.setHttpstatus(HttpStatus.BAD_REQUEST);
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
			}
		} else {
			response.setMessage("Admin not exist");
			response.setStatus(false);
			response.setHttpstatus(HttpStatus.BAD_REQUEST);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}
		if (request.getId() == null || request.getId() == 0) {
			response.setMessage("ID can not be null or zero!");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}

		try {

			boolean status = adminService.deleteAdmin(adminNoMasterDetails);

			if (status) {
				response.setStatus(true);
				response.setMessage("Admin Deleted Successfully!");

				auditLogService.addAuditLog(Constant.ADMINDELETE, Constant.ADMINDELETEDESC, Constant.USERNAMEFROMTOKEN,
						Constant.ADMINDELETESTATUS, Constant.TYPE);

				response.setHttpstatus(HttpStatus.OK);
			} else {

				response.setMessage("Admin Not Found!");
				return ResponseEntity.status(HttpStatus.OK).body(response);

			}
		} catch (Exception e) {
			e.printStackTrace();

			response.setMessage(Constant.ERROR_API_RESPONSE);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);

		}
		return ResponseEntity.ok(response);
	}

	@PostMapping(value = "/activate/deActivate")
	public ResponseEntity<BasicApiResponse> activeordisableAdmin(@RequestBody IdRequest request) {

		BasicApiResponse response = new BasicApiResponse();
		response.setData(new EmptyJsonBody());
		response.setStatus(false);

		if (request.getId() == null || request.getId() == 0) {
			response.setMessage("ID can not be null or zero!");
			return ResponseEntity.ok(response);
		}

		try {
			Admin admin = adminService.disableoractiveAdmin(request.getId());

			if (admin != null) {
				response.setStatus(true);

				response.setHttpstatus(HttpStatus.OK);
				if (admin.isActive()) {

					auditLogService.addAuditLog(Constant.ADMINACTIVATE, Constant.ADMINACTIVATEDES,
							Constant.USERNAMEFROMTOKEN, Constant.ADMINACTIVATESATUS, Constant.TYPE);

					response.setMessage("Admin Activate Successfully!");

				} else {
					auditLogService.addAuditLog(Constant.ADMINDEACTIVATE, Constant.ADMINDEACTIVATEDES,
							Constant.USERNAMEFROMTOKEN, Constant.ADMINDEACTIVATESTATUS, Constant.TYPE);
					response.setMessage("Admin Deactivated successfully!");

				}
			} else {

				response.setMessage("Admin Not Found!");
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);

			}

		} catch (Exception e) {
			e.printStackTrace();

			response.setMessage(Constant.ERROR_API_RESPONSE);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);

		}
		return ResponseEntity.ok(response);
	}

	@GetMapping("/download")
	public ResponseEntity<InputStreamResource> exportExcel(@RequestParam String serachType,
			HttpServletResponse httpServletResponse) throws CustomException, DocumentException, IOException {

		if (serachType.equals("excel")) {
			try {
				String startdate = "";
				String enddate = "";

				String username = null;

				Date dateFrom = null;
				Date dateTo = null;

				if (!startdate.equalsIgnoreCase("") && !enddate.equalsIgnoreCase("")) {
					dateFrom = Util.getSearchDateFromString(startdate);
					dateTo = Util.getSearchDateFromString(enddate);
				}
				byte[] data;

				if (username == null) {
					data = adminService.exportExcel(dateFrom, dateTo);
				} else {
					data = adminService.exportExcelByUsername(username, dateFrom, dateTo);
				}
				Date currentDate = new Date();
				SimpleDateFormat formatter = new SimpleDateFormat("dd-M-yyyy_HH-mm-ss");
				String strDate = formatter.format(currentDate);

				HttpHeaders headers = new HttpHeaders();
				headers.add("Content-Disposition",
						"attachment; filename=shieldcryptAdminManagementDetails_" + strDate + ".xlsx");

				auditLogService.addAuditLog(Constant.ADMINDOWNLOADEXCEL, Constant.ADMINDOWNLOADEXCELDES,
						Constant.USERNAMEFROMTOKEN, Constant.ADMINDOWNLOADEXCELSTATUS, Constant.TYPE);

				return ResponseEntity.ok().headers(headers)
						.contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
						.body(new InputStreamResource(new ByteArrayInputStream(data)));

			} catch (Exception e) {
				e.printStackTrace();

			}
			return null;
		} else {
			httpServletResponse.setContentType("application/pdf");
			DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
			String currentDateTime = dateFormatter.format(new Date());
			String headerKey = "Content-Disposition";
			String headerValue = "attachment; filename=shieldcryptAdminManagementDetails_" + currentDateTime + ".pdf";
			httpServletResponse.setHeader(headerKey, headerValue);
			String sortColumn = "firstName";
			Sort sort = Sort.by(sortColumn).ascending();
			List<Admin> sorting = adminRepo.getAdminMasterDetails(sort);

			AdminPDFExporter exporter = new AdminPDFExporter(sorting);
			exporter.export(httpServletResponse);
			auditLogService.addAuditLog(Constant.ADMINDOWNLOADPDF, Constant.ADMINDOWNLOADPDFDES,
					Constant.USERNAMEFROMTOKEN, Constant.ADMINDOWNLOADPDFSTATUS, Constant.TYPE);

		}
		return null;
	}

	@PostMapping(value = "/updateProfile")
	public ResponseEntity<BasicApiResponse> updateProfile(@RequestBody UpdateProfileRequest request) {
		BasicApiResponse response = new BasicApiResponse();
		response.setData(new EmptyJsonBody());
		response.setStatus(false);

		if (request.getId() == null || request.getId() == 0) {
			response.setMessage("ID can not be null or zero!");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);

		}

		if (request.getFirstName() == null || request.getFirstName().isBlank()) {
			response.setMessage("Invalid First Name!");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}

		if (request.getLastName() == null || request.getLastName().isBlank()) {
			response.setMessage("Invalid Last Name!");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}

		try {
			Admin up_admin = adminService.updateAdminProfile(request);
			if (up_admin != null) {
				response.setStatus(true);
				response.setMessage("Admin Updated Successfully!");
				response.setData(up_admin);

				auditLogService.addAuditLog(Constant.ADMINUPDATEPROFILE, Constant.ADMINUPDATEPROFILEDES,
						Constant.USERNAMEFROMTOKEN, Constant.ADMINUPDATEPROFILESTATUS, Constant.TYPE);

			} else {
				response.setMessage("Admin Not Found!");
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);

			}
		} catch (Exception e) {
			e.printStackTrace();
			response.setMessage(Constant.ERROR_API_RESPONSE);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);

		}
		return ResponseEntity.ok(response);
	}

	@Hidden
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

}