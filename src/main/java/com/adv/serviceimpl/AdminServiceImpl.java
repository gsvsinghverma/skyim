package com.adv.serviceimpl;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;
import org.json.JSONObject;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.adv.exception.CustomException;
import com.adv.model.Admin;
import com.adv.model.AdminRoles;
import com.adv.model.CpuUsage;
import com.adv.model.Designation;
import com.adv.model.Location;
import com.adv.model.MailConfiguration;
import com.adv.model.MemoryUsage;
import com.adv.model.PasswordPolicy;
import com.adv.model.Unit;
import com.adv.model.User;
import com.adv.pagin.GetAllRequest;
import com.adv.pagin.Sorting;
import com.adv.payloads.BasicResponsePayload;
import com.adv.payloads.BasicResponsePayloadForLocation;
import com.adv.payloads.apirequests.CpuUsagePayload;
import com.adv.payloads.apirequests.MemoryUsageDurationRequest;
import com.adv.payloads.apirequests.MemoryUsagePayload;
import com.adv.payloads.apirequests.ResetPasswordPayload;
import com.adv.payloads.apirequests.SaveAdminRequest;
import com.adv.payloads.apirequests.UpdateAdminRequest;
import com.adv.payloads.apirequests.UpdateProfileRequest;
import com.adv.payloads.apiresponse.BasicApiResponse;
import com.adv.payloads.apiresponse.BasicDiskDetailResponse;
import com.adv.payloads.apiresponse.CalllogCountDesignation;
import com.adv.payloads.apiresponse.CalllogCountLocation;
import com.adv.payloads.apiresponse.CalllogCountUnit;
import com.adv.payloads.apiresponse.DeshboadCountApiResponse;
import com.adv.payloads.apiresponse.DeshboadCountApiResponseDesignation;
import com.adv.payloads.apiresponse.DeshboadCountApiResponseUnit;
import com.adv.projections.CountProjections;
import com.adv.repository.AdminRepository;
import com.adv.repository.AdminRolesRepository;
import com.adv.repository.CpuUsageRepository;
import com.adv.repository.DesignationRepository;
import com.adv.repository.GroupManagementRepository;
import com.adv.repository.InvalidationLoginRepository;
import com.adv.repository.LocationRepository;
import com.adv.repository.MailCRepository;
import com.adv.repository.MemoryUsageRepository;
import com.adv.repository.ModulesRepository;
import com.adv.repository.PasswordPolicyRepository;
import com.adv.repository.UnitRepository;
import com.adv.repository.UserRepository;
import com.adv.service.AdminService;
import com.adv.util.Constant;
import com.adv.util.ExcelUtil;
import com.adv.util.PasswordPolicyUtil2;
import com.adv.util.PasswordPolicyUtil3;
import com.adv.util.SortingImp;
import com.adv.util.Util;
import com.adv.utilmail.OutlookMailService;

@Service
public class AdminServiceImpl implements AdminService {

	@Value("${asteriskUrl}")
	private String asteriskUrl;

	@Value("${asteriskApISecretKey}")
	private String asteriskApISecretKey;

	@Value("${file.upload-dir.url}")
	private String fileUploadUrl;

	@Value("${file.upload-dir}")
	private String fileUploadPath;

	@Autowired
	CpuUsageRepository cpuUsageRepository;

	@Autowired
	MemoryUsageRepository memoryUsageRepository;

	@Autowired
	MailCRepository mcrepo;

	@Autowired
	PasswordPolicyRepository passwordPolicyRepository;

	@Autowired
	AdminRepository adminRepository;

	@Autowired
	InvalidationLoginRepository invalidRepo;

	@Autowired
	ModulesRepository moduleRepository;

	@Autowired
	AdminRolesRepository adminRolesRespository;

	@Autowired
	GroupManagementRepository groupRepo;

	@Autowired
	UserRepository userRepo;

	@Autowired
	UnitRepository unitRepository;

	@Autowired
	DesignationRepository designationRepository;

	@Autowired
	LocationRepository locationRepo;

	org.slf4j.Logger LOGGER = LoggerFactory.getLogger(AdminServiceImpl.class);

	@Override
	public Admin loginAdmin(String userName, String password) {
		return adminRepository.findByUsernameAndPassword(userName, password);
	}

	@Override
	public Admin saveAdminDetails(String username) {
		try {
			Admin adminValue = adminRepository.findByUsername(username);
			if (adminValue == null) {
				Admin newUser = new Admin();
				newUser.setId(Constant.ADMIN_ID);
				newUser.setUsername(Constant.ADMIN_USER_NAME.trim());
				newUser.setFirstName(Constant.ADMIN_FIRST_NAME.trim());
				newUser.setLastName(Constant.ADMIN_LAST_NAME.trim());

				Timestamp currentDT = new Timestamp(System.currentTimeMillis());
				newUser.setCreationDate(currentDT);
				newUser.setUpdationDate(currentDT);

				String enc_pass = DigestUtils.md5Hex(Constant.ADMIN_PASSWORD.trim());
				newUser.setPassword(enc_pass);

				newUser.setActive(true);

				newUser.setMobileNumber("1234567890");
				newUser.setEmail("admin@gmail.com");
				newUser.setProfilePhoto("");
				newUser.setIsmasteradmin(true);
				Location location = locationRepo.findById(1l).orElse(null);
				if (location != null)
					newUser.setLocation(location);

				Unit unit = unitRepository.findById(1l).orElse(null);
				if (unit != null)
					newUser.setUnit(unit);

				Designation designation = designationRepository.findById(1l).orElse(null);
				if (designation != null)
					newUser.setDesignation(designation);

				AdminRoles role = adminRolesRespository.findRole(1l);
				if (role != null)
					newUser.setRole(role);

				return adminRepository.save(newUser);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Admin get(Long id) {

		Admin admin = adminRepository.findById(id).orElse(null);

		if (admin != null) {
			if (Constant.MASTERFLAG == true) {
				if (!admin.isDeleted()) {
					return admin;
				}

			} else {
				Admin fetchAdminMasterData = adminRepository.fetchAdminMasterData(id);
				if (fetchAdminMasterData != null) {
					return fetchAdminMasterData;
				}

				return null;
			}

		}
		Constant.MASTERFLAG = false;
		return null;

	}

	@Override
	public Admin getactive(Long id) {

		return adminRepository.findById(id).orElse(null);

	}

	@Override
	public Admin gets(Long id) {

		Admin admin = adminRepository.findById(id).orElseThrow(null);

			if(admin != null && !admin.isDeleted()) {
				return admin;
			}
		
		return null;

	}

	@Override
	public List<Admin> getAllAdminData() {

		List<Admin> adminnomasterdetails = adminRepository.getAdminNonMasterDetails();

		return adminnomasterdetails;
	}

	@Override
	public boolean isAdminExistByUserIdAndModuleId(Long admin_id, Long module_id) {

		return true;
	}

	@Override
	public BasicApiResponse saveAdmin(SaveAdminRequest user) {
		BasicApiResponse response = new BasicApiResponse();
		Admin newUser = new Admin();
		newUser.setFirstName(user.getFirstName().trim());
		newUser.setLastName(user.getLastName().trim());
		newUser.setUsername(user.getUsername().trim());

		String enc_pass = DigestUtils.md5Hex(user.getPassword().trim());
		PasswordPolicy passwordPolicy = passwordPolicyRepository.getByPasswordFor();
		try {
			response = PasswordPolicyUtil2.passwordPolicyCheck(passwordPolicy, user.getPassword());

			if (response.isStatus() == true) {
				newUser.setPassword(enc_pass);

				newUser.setMobileNumber(user.getMobileNumber());
				newUser.setEmail(user.getEmail());

				Location location = locationRepo.findById(user.getLocation()).orElse(null);
				if (location != null)
					newUser.setLocation(location);

				Unit unit = unitRepository.findById(user.getUnit()).orElse(null);
				if (unit != null)
					newUser.setUnit(unit);

				Designation designation = designationRepository.findById(user.getDesignation()).orElse(null);
				if (designation != null)
					newUser.setDesignation(designation);

				AdminRoles role = adminRolesRespository.findById(user.getRole()).orElse(null);
				if (role != null)
					newUser.setRole(role);

				Timestamp currentDT = new Timestamp(System.currentTimeMillis());
				newUser.setCreationDate(currentDT);
				newUser.setUpdationDate(currentDT);
				newUser.setActive(true);
				newUser.setProfilePhoto("");

				adminRepository.save(newUser);
				response.setMessage("Admin Added successfully");
				response.setData(newUser);
				return response;
			} else if (response.isStatus() == false) {
				return response;
			}

			else {
				response.setMessage("Admin not added");
				return response;
			}
		} catch (Exception e) {

			e.printStackTrace();
		}
		return null;

	}

	@Override
	public boolean deleteAdmin(Admin admin) {
		try {

			admin.setDeleted(true);
			adminRepository.save(admin);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public Admin disableoractiveAdmin(Long id) {
		try {
			Admin admin = this.getactiveAnddeactive(id);
			if (admin != null) {

				if (admin.isActive()) {

					admin.setActive(false);
				} else {
					admin.setActive(true);
				}
				Timestamp currentDT = new Timestamp(System.currentTimeMillis());

				admin.setUpdationDate(currentDT);
				return adminRepository.save(admin);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public Admin getactiveAnddeactive(long id) {
		Admin admin = adminRepository.findById(id).orElseThrow(null);
		if (admin != null && !admin.isDeleted()) {

			return admin;

		}
		return null;
	}

	@Override
	public Admin updateAdmin(UpdateAdminRequest admin) {
		try {
			Admin adminOld = adminRepository.findById(admin.getId()).orElse(null);
			if (adminOld.isDeleted() == false) {
				if (adminOld != null) {
					adminOld.setUsername(admin.getUsername().trim());
					adminOld.setFirstName(admin.getFirstName().trim());
					adminOld.setLastName(admin.getLastName().trim());

					Timestamp currentDT = new Timestamp(System.currentTimeMillis());
					adminOld.setUpdationDate(currentDT);

					adminOld.setMobileNumber(admin.getMobileNumber());
					adminOld.setEmail(admin.getEmail());
					Location location = locationRepo.findById(admin.getLocation()).orElse(null);
					if (location != null)
						adminOld.setLocation(location);

					Unit unit = unitRepository.findById(admin.getUnit()).orElse(null);
					if (unit != null)
						adminOld.setUnit(unit);

					Designation designation = designationRepository.findById(admin.getDesignation()).orElse(null);
					if (designation != null)
						adminOld.setDesignation(designation);

					AdminRoles role = adminRolesRespository.findRole(admin.getRole());

					if (role != null)
						adminOld.setRole(role);

					return adminRepository.save(adminOld);
				}
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public boolean existsByUserName(String username) {
		return adminRepository.existsByUsername(username);
	}

	@Override
	public boolean findByUserNameUpdate(String username, Long id) {
		Admin admin = adminRepository.findByUsername(username);
		if (admin != null) {
			if (admin.getId() == id)
				return false;
			else
				return true;
		} else
			return false;
	}

	@Override
	public byte[] exportExcel(Date dateFrom, Date dateTo) throws CustomException {
		return ExcelUtil.downloadAdmins(adminForExport(dateFrom, dateTo));
	}

	private List<Admin> adminForExport(Date dateFrom, Date dateTo) {
		if (dateFrom != null && dateTo != null) {

			return null;
		} else {

			String sortColumn = "firstName";
			Sort sort = Sort.by(sortColumn).ascending();
			return adminRepository.getAdminMasterDetails(sort);
		}
	}

	@Override
	public byte[] exportExcelByUsername(String username, Date dateFrom, Date dateTo) throws CustomException {

		return null;
	}

	@Override
	public Page<Admin> getAllAdminsPageData(Pageable pageable) {
		Page<Admin> page = adminRepository.findAll(pageable);
		return page;
	}

	@Override
	public BasicResponsePayload updateProfilePhoto(MultipartFile file, String username) {

		BasicResponsePayload response = new BasicResponsePayload();
		String fileUrl = "";

		try {
			Admin admin = adminRepository.findByUsername(username);

			if (admin != null) {

				File fileF = new File(file.getOriginalFilename());
				String fileName = fileF.getName();

				byte[] bytes = file.getBytes();

				if (admin.getProfilePhoto() != null && !admin.getProfilePhoto().equals("")) {
					Files.deleteIfExists(Paths.get(admin.getProfilePhoto().replace(fileUploadUrl, fileUploadPath)));
				}

				Path path = Paths.get(fileUploadPath + "profilephotos/");
				Files.createDirectories(path);

				Path path2 = Paths.get(fileUploadPath + "profilephotos/" + "/" + admin.getUsername() + "_" + fileName);
				Files.write(path2, bytes);

				fileUrl = fileUploadUrl + "profilephotos/" + "/" + admin.getUsername() + "_" + fileName;

				admin.setProfilePhoto(fileUrl);

				if (adminRepository.save(admin) != null) {
					response.setStatus("1");
					response.setData(fileUrl);
				} else {
					response.setStatus("3");
				}

			} else {
				response.setStatus("2");
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.setStatus("4");
		}

		response.setData(fileUrl);
		return response;
	}

	@Override
	public List<Admin> listAll(Sorting sort) {

		String sortColumn = sort.getColumn();
		String dir = sort.getDirection();
		if (dir.equalsIgnoreCase("ASC")) {
			Sort.by(sortColumn).ascending();
		} else {
			Sort.by(sortColumn).descending();
		}

		return adminRepository.findAll();
	}

	@Override
	public Page<Admin> getAllAdmins(GetAllRequest pagingRequest) {

		String sortColumn = "creationDate";

		String sortColumnsearch = "creation_date";
		Sort sort = Sort.by(sortColumn).descending();
		Sort sortsearch = Sort.by(sortColumnsearch).descending();

		Pageable paging = null;

		if (pagingRequest.getSort() != null) {
			LOGGER.info("enter into sort coloumn");
			if (pagingRequest.getSort().getColumn() != null && pagingRequest.getSort().getDirection() != null) {

				sortColumn = pagingRequest.getSort().getColumn();
				sortColumn = SortingImp.getSort(sortColumn);
				sortColumnsearch = sortColumn;

				String dir = pagingRequest.getSort().getDirection();

				if (dir.equalsIgnoreCase("ASC")) {
					sort = Sort.by(sortColumn).ascending();
					sortsearch = Sort.by(sortColumnsearch).ascending();
				} else {
					sort = Sort.by(sortColumn).descending();
					sortsearch = Sort.by(sortColumnsearch).descending();
				}

				paging = PageRequest.of(pagingRequest.getPage(), pagingRequest.getSize(), sort);
			}
		} else {

			LOGGER.info("enter into line 512");

			paging = PageRequest.of(pagingRequest.getPage(), pagingRequest.getSize(), sort);
			LOGGER.info("Paging data:");
		}

		Page<Admin> admins = null;

		if (pagingRequest.getSearch() != null) {

			String firstName = null;
			String lastName = null;
			String userName = null;
			String email = null;
			String mobileNumber = null;
			Long locationid = null;
			Long designationid = null;
			Long unitid = null;
			Long roleid = null;

			Integer searchBy1 = null;
			Integer searchBy2 = null;
			Integer searchBy3 = null;
			Integer searchBy4 = null;
			Integer searchBy5 = null;
			Integer searchBy6 = null;
			Integer searchBy7 = null;
			Integer searchBy8 = null;
			Integer searchBy9 = null;

			int size = pagingRequest.getSearch().size();

			for (int i = 0; i < size; i++) {

				if (pagingRequest.getSearch().get(i).getColumn().equalsIgnoreCase("firstname")) {

					firstName = pagingRequest.getSearch().get(i).getValue();

					searchBy1 = pagingRequest.getSearch().get(i).getSearchBy();

				}
				if (pagingRequest.getSearch().get(i).getColumn().equalsIgnoreCase("lastname")) {

					lastName = pagingRequest.getSearch().get(i).getValue();

					searchBy2 = pagingRequest.getSearch().get(i).getSearchBy();

				}

				if (pagingRequest.getSearch().get(i).getColumn().equalsIgnoreCase("username")) {
					userName = pagingRequest.getSearch().get(i).getValue();
					searchBy3 = pagingRequest.getSearch().get(i).getSearchBy();

				}
				if (pagingRequest.getSearch().get(i).getColumn().equalsIgnoreCase("email")) {

					email = pagingRequest.getSearch().get(i).getValue();
					searchBy4 = pagingRequest.getSearch().get(i).getSearchBy();

				}

				if (pagingRequest.getSearch().get(i).getColumn().equalsIgnoreCase("mobileNumber")) {

					mobileNumber = pagingRequest.getSearch().get(i).getValue();
					searchBy5 = pagingRequest.getSearch().get(i).getSearchBy();

				}

				if (pagingRequest.getSearch().get(i).getColumn().equalsIgnoreCase("location")) {

					locationid = Long.valueOf(pagingRequest.getSearch().get(i).getValue());
					searchBy6 = pagingRequest.getSearch().get(i).getSearchBy();

				}
				if (pagingRequest.getSearch().get(i).getColumn().equalsIgnoreCase("designation")) {

					designationid = Long.valueOf(pagingRequest.getSearch().get(i).getValue());
					searchBy7 = pagingRequest.getSearch().get(i).getSearchBy();

				}
				if (pagingRequest.getSearch().get(i).getColumn().equalsIgnoreCase("unit")) {

					unitid = Long.valueOf(pagingRequest.getSearch().get(i).getValue());
					searchBy8 = pagingRequest.getSearch().get(i).getSearchBy();

				}

				if (pagingRequest.getSearch().get(i).getColumn().equalsIgnoreCase("role")) {

					roleid = Long.valueOf(pagingRequest.getSearch().get(i).getValue());
					searchBy9 = pagingRequest.getSearch().get(i).getSearchBy();

				}

			}
			paging = PageRequest.of(pagingRequest.getPage(), pagingRequest.getSize(), sortsearch);

			admins = adminRepository.findByContainsSearch(firstName, lastName, userName, email, mobileNumber,
					locationid, designationid, unitid, roleid, searchBy1, searchBy2, searchBy3, searchBy4, searchBy5,
					searchBy6, searchBy7, searchBy8, searchBy9, paging);

			return admins;

		}

		if (pagingRequest.getDate() != null) {
			if (pagingRequest.getDate().getFrom() != null && pagingRequest.getDate().getTo() != null) {
				String from = pagingRequest.getDate().getFrom();
				String to = pagingRequest.getDate().getTo();

				Date fromDate = Util.getSearchDate(from);
				Date toDate = Util.getSearchDate(to);
				return adminRepository.findByCreationDateBetweenAndIsmasteradminIsAndActive(fromDate, toDate, false,
						true, paging);

			} else {

				admins = adminRepository.findBySearchNonMaster(false, paging);

			}
		} else {

			for (Sort.Order order : sort) {
				if (order.getProperty().equals("creationDate")) {

					String dir = order.getDirection().toString();
					if (dir.equalsIgnoreCase("ASC")) {

						sortColumn = "creation_date";
						sort = Sort.by(sortColumn).ascending();

					} else {

						sortColumn = "creation_date";
						sort = Sort.by(sortColumn).descending();

					}
					paging = PageRequest.of(pagingRequest.getPage(), pagingRequest.getSize(), sort);

				}

			}
			admins = adminRepository.findBySearchNonMaster(false, paging);

		}

		if (admins != null && !admins.isEmpty() && admins.getContent() != null) {
			return admins;

		} else {
			return admins;

		}
	}

	@Override
	public BasicApiResponse changePassword(String username, String password, String newPassword) {
		BasicApiResponse response = new BasicApiResponse();

		try {

			String oldEncPass = DigestUtils.md5Hex(password);
			String newEncPass = DigestUtils.md5Hex(newPassword);

			Admin admin1 = adminRepository.findBypass(oldEncPass);

			Admin admin = adminRepository.findusernameandnotdeleted(username);

			if (admin == null) {
				response.setMessage("InValid Username !");
				response.setStatus(false);
				return response;

			}
			if (admin1 == null) {
				response.setMessage("InValid Password !");
				response.setStatus(false);
				return response;

			}

				if(admin != null && admin.isDeleted() == false) {
					PasswordPolicy passwordPolicy = passwordPolicyRepository.getByPasswordFor();

					if (passwordPolicy.isLastPasswordCheck()) {

						if (admin.getPassword().equals(newEncPass)) {
							response.setStatus(false);
							response.setMessage("New Password Can Not Be Equal Old Password!");
							return response;
						} else {
							response = PasswordPolicyUtil3.passwordPolicyCheck(passwordPolicy, newPassword);

							if (response.isStatus() == true) {
								String enc_pass = DigestUtils.md5Hex(newPassword.trim());
								admin.setPassword(enc_pass);
								adminRepository.save(admin);
								response.setMessage("Password Changed SucessFully!");
								return response;

							} else if (response.isStatus() == false) {

								return response;
							} else {
								return response;
							}
						}

					}
				}
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		return response;
	}

	@Override
	public BasicApiResponse resetPassword(String username, String newPassword) {
		BasicApiResponse response = new BasicApiResponse();
		try {

			String enc_pass = DigestUtils.md5Hex(newPassword.trim());
			Admin admin = adminRepository.findByUsername(username);
			if (admin != null) {
				PasswordPolicy passwordPolicy = passwordPolicyRepository.getByPasswordFor();

				if (passwordPolicy.isLastPasswordCheck()) {
					if (admin.getPassword().equals(enc_pass)) {
						response.setStatus(false);
						response.setMessage("New Password Can Not Be Equal Old Password!");
						return response;
					} else {
						response = PasswordPolicyUtil3.passwordPolicyCheck(passwordPolicy, newPassword);

						if (response.isStatus() == true) {
							Timestamp currentDT = new Timestamp(System.currentTimeMillis());
							admin.setUpdationDate(currentDT);
							admin.setPassword(enc_pass);
							adminRepository.save(admin);
							response.setMessage("Password Reset SucessFully!");
							response.setData(admin);
							return response;

						} else if (response.isStatus() == false) {
							return response;
						} else {
							return response;
						}

					}

				}

				else {
					response = PasswordPolicyUtil3.passwordPolicyCheck(passwordPolicy, newPassword);

					if (response.isStatus() == true) {
						Timestamp currentDT = new Timestamp(System.currentTimeMillis());
						admin.setUpdationDate(currentDT);
						admin.setPassword(enc_pass);
						adminRepository.save(admin);
						response.setMessage("Password Reset SucessFully!");
						response.setData(admin);
						return response;

					} else if (response.isStatus() == false) {
						return response;
					} else {
						return response;
					}
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;

	}

	@Override
	public Admin updateAdminProfile(UpdateProfileRequest request) {
		try {
			Admin adminOld = adminRepository.findById(request.getId()).orElse(null);
			if (adminOld != null) {
				adminOld.setFirstName(request.getFirstName().trim());
				adminOld.setLastName(request.getLastName().trim());

				Timestamp currentDT = new Timestamp(System.currentTimeMillis());
				adminOld.setUpdationDate(currentDT);

				adminOld.setEmail(request.getEmail());

				return adminRepository.save(adminOld);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public BasicApiResponse resetPasswords(ResetPasswordPayload request) {
		BasicApiResponse apiresponse = new BasicApiResponse();
		try {

			String msg = "";

			Admin admin = adminRepository.findByUsername(request.getUsername());

			List<MailConfiguration> mailconfig = mcrepo.findAll();

			OutlookMailService outlook = new OutlookMailService();

			Map<Admin, String> map = outlook.MailVerification(admin.getEmail(), request.getUsername(), admin,
					mailconfig);

			for (Admin key : map.keySet()) {
				adminRepository.save(key);
			}

			apiresponse.setStatus(true);
			apiresponse.setHttpstatus(HttpStatus.OK);
			for (String value : map.values()) {
				msg = value;
			}

			apiresponse.setMessage(msg);

		} catch (Exception e) {

			e.printStackTrace();
			apiresponse.setStatus(false);
			apiresponse.setMessage(e.getStackTrace().toString());
			apiresponse.setHttpstatus(HttpStatus.BAD_REQUEST);
			return apiresponse;

		}
		return apiresponse;

	}

	@Override
	public CountProjections getcountdetails() {

		CountProjections cp = new CountProjections();
		long countadmin = adminRepository.count();
		long countuser = userRepo.countByIsDeleted(false);
		long countgroup = groupRepo.countByIsDeleted(false);

		cp.setAdmincount(countadmin);

		cp.setGroupcount(countgroup);
		cp.setUsercount(countuser);

		return cp;
	}

	@Override
	public boolean existsByEmail(String email) {
		return adminRepository.existsByEmail(email);
	}

	@Override
	public List<MemoryUsagePayload> getmemoryUsagedetails(MemoryUsageDurationRequest rquest) {
		List<MemoryUsage> memoryUsageList = null;

		List<MemoryUsagePayload> memoryUsagePayloadList = new ArrayList<MemoryUsagePayload>();
	
		Timestamp currentDT = new Timestamp(System.currentTimeMillis());
		ZonedDateTime zonedDateTime = currentDT.toInstant().atZone(ZoneId.of("UTC"));

		if (rquest.getDurationTypeId() == 1) {
			Timestamp newdate = Timestamp.from(zonedDateTime.plus(-7, ChronoUnit.DAYS).toInstant());
			memoryUsageList = memoryUsageRepository.findByCreationdate(newdate);
			for(int i=0;i<memoryUsageList.size();i++) {
			MemoryUsagePayload memoryUsagePayload = new MemoryUsagePayload();
			memoryUsagePayload.setFreePhysicalMemorySize(memoryUsageList.get(i).getFreePhysicalMemorySize());
			memoryUsagePayload.setTotalPhysicalMemorySize(memoryUsageList.get(i).getTotalPhysicalMemorySize());
			memoryUsagePayload.setUsagePhysicalMemorySize(memoryUsageList.get(i).getUsagePhysicalMemorySize());
			memoryUsagePayload.setCreationDate(memoryUsageList.get(i).getCreationDate());
			memoryUsagePayloadList.add(memoryUsagePayload);
			}
		}

		else if (rquest.getDurationTypeId() == 2) {
			Timestamp newdate = Timestamp.from(zonedDateTime.plus(-15, ChronoUnit.DAYS).toInstant());
			memoryUsageList = memoryUsageRepository.findByCreationdate(newdate);
			
			for(int i=0;i<memoryUsageList.size();i++) {
				MemoryUsagePayload memoryUsagePayload = new MemoryUsagePayload();
				memoryUsagePayload.setFreePhysicalMemorySize(memoryUsageList.get(i).getFreePhysicalMemorySize());
				memoryUsagePayload.setTotalPhysicalMemorySize(memoryUsageList.get(i).getTotalPhysicalMemorySize());
				memoryUsagePayload.setUsagePhysicalMemorySize(memoryUsageList.get(i).getUsagePhysicalMemorySize());
				memoryUsagePayload.setCreationDate(memoryUsageList.get(i).getCreationDate());
				memoryUsagePayloadList.add(memoryUsagePayload);
				}

		}

		else if (rquest.getDurationTypeId() == 3) {
			Timestamp newdate = Timestamp.from(zonedDateTime.plus(-24, ChronoUnit.DAYS).toInstant());
			memoryUsageList = memoryUsageRepository.findByCreationdate(newdate);
			
			for(int i=0;i<memoryUsageList.size();i++) {
				MemoryUsagePayload memoryUsagePayload = new MemoryUsagePayload();
				memoryUsagePayload.setFreePhysicalMemorySize(memoryUsageList.get(i).getFreePhysicalMemorySize());
				memoryUsagePayload.setTotalPhysicalMemorySize(memoryUsageList.get(i).getTotalPhysicalMemorySize());
				memoryUsagePayload.setUsagePhysicalMemorySize(memoryUsageList.get(i).getUsagePhysicalMemorySize());
				memoryUsagePayload.setCreationDate(memoryUsageList.get(i).getCreationDate());
				memoryUsagePayloadList.add(memoryUsagePayload);
				}
		}

		else if (rquest.getDurationTypeId() == 4) {
			Timestamp newdate = Timestamp.from(zonedDateTime.plus(-30, ChronoUnit.DAYS).toInstant());
			memoryUsageList = memoryUsageRepository.findByCreationdate(newdate);

			for(int i=0;i<memoryUsageList.size();i++) {
				MemoryUsagePayload memoryUsagePayload = new MemoryUsagePayload();
				memoryUsagePayload.setFreePhysicalMemorySize(memoryUsageList.get(i).getFreePhysicalMemorySize());
				memoryUsagePayload.setTotalPhysicalMemorySize(memoryUsageList.get(i).getTotalPhysicalMemorySize());
				memoryUsagePayload.setUsagePhysicalMemorySize(memoryUsageList.get(i).getUsagePhysicalMemorySize());
				memoryUsagePayload.setCreationDate(memoryUsageList.get(i).getCreationDate());
				memoryUsagePayloadList.add(memoryUsagePayload);
				}
		}

		else if (rquest.getDurationTypeId() == 5) {
			Timestamp newdate = Timestamp.from(zonedDateTime.plus(-1, ChronoUnit.HOURS).toInstant());
			memoryUsageList = memoryUsageRepository.findByCreationdate(newdate);

			for(int i=0;i<memoryUsageList.size();i++) {
				MemoryUsagePayload memoryUsagePayload = new MemoryUsagePayload();
				memoryUsagePayload.setFreePhysicalMemorySize(memoryUsageList.get(i).getFreePhysicalMemorySize());
				memoryUsagePayload.setTotalPhysicalMemorySize(memoryUsageList.get(i).getTotalPhysicalMemorySize());
				memoryUsagePayload.setUsagePhysicalMemorySize(memoryUsageList.get(i).getUsagePhysicalMemorySize());
				memoryUsagePayload.setCreationDate(memoryUsageList.get(i).getCreationDate());
				memoryUsagePayloadList.add(memoryUsagePayload);
				}

		}

		else if (rquest.getDurationTypeId() == 6) {
			Timestamp newdate = Timestamp.from(zonedDateTime.plus(-7, ChronoUnit.HOURS).toInstant());
			memoryUsageList = memoryUsageRepository.findByCreationdate(newdate);

			for(int i=0;i<memoryUsageList.size();i++) {
				MemoryUsagePayload memoryUsagePayload = new MemoryUsagePayload();
				memoryUsagePayload.setFreePhysicalMemorySize(memoryUsageList.get(i).getFreePhysicalMemorySize());
				memoryUsagePayload.setTotalPhysicalMemorySize(memoryUsageList.get(i).getTotalPhysicalMemorySize());
				memoryUsagePayload.setUsagePhysicalMemorySize(memoryUsageList.get(i).getUsagePhysicalMemorySize());
				memoryUsagePayload.setCreationDate(memoryUsageList.get(i).getCreationDate());
				memoryUsagePayloadList.add(memoryUsagePayload);
				}

		}

		return memoryUsagePayloadList;
	}
	
	

	@Override
	public void savememoryUsageFirstTime() {

		List<MemoryUsage> meetingRecurrence = memoryUsageRepository.findAll();

		if (meetingRecurrence.isEmpty()) {
			String s;
			Process p;
			try {
				p = Runtime.getRuntime().exec("cat /proc/meminfo");
				BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
				int count = 0;
				Long totalmemory = null;
				Long MemFree = null;
				Long Buffers = null;
				Long Cached = null;
				Long Slab = null;
				Long usedMemory = null;
				Long feeMemory = null;

				while ((s = br.readLine()) != null) {
					String[] memvalue = s.split("\\s");

					for (int j = 0; j < memvalue.length; j++) {
						String value = memvalue[j].trim();
						String regex = "[0-9]+";
						boolean matches = value.matches(regex);
						if (matches == true && count == 0) {
							totalmemory = (Long.parseLong(value)) / 1024;

						}
						if (matches == true && count == 1) {
							MemFree = (Long.parseLong(value)) / 1024;

						}

						if (matches == true && count == 3) {
							Buffers = (Long.parseLong(value)) / 1024;

						}

						if (matches == true && count == 4) {
							Cached = (Long.parseLong(value)) / 1024;

						}

						if (matches == true && count == 21) {
							Slab = (Long.parseLong(value)) / 1024;

						}

					}
					count++;
				}

				usedMemory = totalmemory - MemFree - Buffers - Cached - Slab;
				feeMemory = totalmemory - usedMemory;

				MemoryUsage memory = new MemoryUsage();
				memory.setFreePhysicalMemorySize(feeMemory);
				memory.setTotalPhysicalMemorySize(totalmemory);
				memory.setUsagePhysicalMemorySize(usedMemory);
				Timestamp currentDT = new Timestamp(System.currentTimeMillis());
				memory.setCreationDate(currentDT);
				memoryUsageRepository.save(memory);
				ZonedDateTime zonedDateTime = currentDT.toInstant().atZone(ZoneId.of("UTC"));
				Timestamp newdate = Timestamp.from(zonedDateTime.plus(-30, ChronoUnit.DAYS).toInstant());

				memoryUsageRepository.removeOlderThan(newdate);
				p.waitFor();

				p.destroy();
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}

	@Override
	public BasicApiResponse getAllDiskDetails() {

		BasicApiResponse basicresp = new BasicApiResponse();
		BasicDiskDetailResponse apiresponse = new BasicDiskDetailResponse();
		if (Util.isUnix()) {
			File root = new File("/");
			long a = root.getTotalSpace();

			long l = root.getTotalSpace();
			long MEGABYTE = 1024L * 1024L;
			long getTotalSpace = l / MEGABYTE;

			long l1 = root.getFreeSpace();
			long MEGABYTE1 = 1024L * 1024L;
			long getFreeSpace = l1 / MEGABYTE1;

			long getUsableSpace = getTotalSpace - getFreeSpace;

			apiresponse.setTotalspace(getTotalSpace);
			apiresponse.setFreespace(getFreeSpace);
			apiresponse.setUsablespace(getUsableSpace);

		} else if (Util.isWindows()) {

		}
		basicresp.setStatus(true);
		basicresp.setData(apiresponse);

		return basicresp;
	}

	@Override
	public void savemecpuUsageFirstTime() {

		List<CpuUsage> cpuusageList = cpuUsageRepository.findAll();
		if (cpuusageList.isEmpty()) {
			OperatingSystemMXBean omx = ManagementFactory.getOperatingSystemMXBean();
			com.sun.management.OperatingSystemMXBean osMxBean = null;

			if (omx instanceof com.sun.management.OperatingSystemMXBean) {
				osMxBean = (com.sun.management.OperatingSystemMXBean) omx;

			}
			CpuUsage cpuUsage = new CpuUsage();
			if (osMxBean != null) {
				Timestamp currentDT = new Timestamp(System.currentTimeMillis());

				double cpu1 = osMxBean.getProcessCpuLoad();
				DecimalFormat df = new DecimalFormat("0.00");
				cpuUsage.setProcessCpuLoad(df.format(cpu1));

				long cpu2 = osMxBean.getProcessCpuTime();
				cpuUsage.setProcessCpuTime(convertTimeStampToStringDate(cpu2));

				double cpu3 = osMxBean.getSystemCpuLoad();
				cpuUsage.setSystemCpuLoad(df.format(cpu3));

				cpuUsage.setCreationDate(currentDT);
				cpuUsageRepository.save(cpuUsage);

			}
		}

	}

	@Override
	public List<CpuUsagePayload> getCpuUsagedetails(MemoryUsageDurationRequest rquest) {
		List<CpuUsage> memoryUsageList = null;

		List<CpuUsagePayload> memoryUsagePayloadList = new ArrayList<CpuUsagePayload>();
		
		Timestamp currentDT = new Timestamp(System.currentTimeMillis());
		ZonedDateTime zonedDateTime = currentDT.toInstant().atZone(ZoneId.of("UTC"));

		if (rquest.getDurationTypeId() == 1) {
			Timestamp newdate = Timestamp.from(zonedDateTime.plus(-7, ChronoUnit.DAYS).toInstant());
			memoryUsageList = cpuUsageRepository.findByCreationdate(newdate);

			for(int i=0;i<memoryUsageList.size();i++) {
				CpuUsagePayload memoryUsagePayload = new CpuUsagePayload();
			memoryUsagePayload.setProcessCpuLoad(memoryUsageList.get(i).getProcessCpuLoad());
			memoryUsagePayload.setSystemCpuLoad(memoryUsageList.get(i).getSystemCpuLoad());
			memoryUsagePayload.setProcessCpuTime(memoryUsageList.get(i).getProcessCpuTime());
			memoryUsagePayload.setCreationDate(memoryUsageList.get(i).getCreationDate());
			memoryUsagePayloadList.add(memoryUsagePayload);
			}
		

		}

		else if (rquest.getDurationTypeId() == 2) {
			Timestamp newdate = Timestamp.from(zonedDateTime.plus(-15, ChronoUnit.DAYS).toInstant());
			memoryUsageList = cpuUsageRepository.findByCreationdate(newdate);

			for(int i=0;i<memoryUsageList.size();i++) {
				CpuUsagePayload memoryUsagePayload = new CpuUsagePayload();
			memoryUsagePayload.setProcessCpuLoad(memoryUsageList.get(i).getProcessCpuLoad());
			memoryUsagePayload.setSystemCpuLoad(memoryUsageList.get(i).getSystemCpuLoad());
			memoryUsagePayload.setProcessCpuTime(memoryUsageList.get(i).getProcessCpuTime());
			memoryUsagePayload.setCreationDate(memoryUsageList.get(i).getCreationDate());
			memoryUsagePayloadList.add(memoryUsagePayload);
			}

		}

		else if (rquest.getDurationTypeId() == 3) {
			Timestamp newdate = Timestamp.from(zonedDateTime.plus(-24, ChronoUnit.DAYS).toInstant());
			memoryUsageList = cpuUsageRepository.findByCreationdate(newdate);

			for(int i=0;i<memoryUsageList.size();i++) {
				CpuUsagePayload memoryUsagePayload = new CpuUsagePayload();
			memoryUsagePayload.setProcessCpuLoad(memoryUsageList.get(i).getProcessCpuLoad());
			memoryUsagePayload.setSystemCpuLoad(memoryUsageList.get(i).getSystemCpuLoad());
			memoryUsagePayload.setProcessCpuTime(memoryUsageList.get(i).getProcessCpuTime());
			memoryUsagePayload.setCreationDate(memoryUsageList.get(i).getCreationDate());
			memoryUsagePayloadList.add(memoryUsagePayload);
			}

		}

		else if (rquest.getDurationTypeId() == 4) {
			Timestamp newdate = Timestamp.from(zonedDateTime.plus(-30, ChronoUnit.DAYS).toInstant());
			memoryUsageList = cpuUsageRepository.findByCreationdate(newdate);

			for(int i=0;i<memoryUsageList.size();i++) {
				CpuUsagePayload memoryUsagePayload = new CpuUsagePayload();
			memoryUsagePayload.setProcessCpuLoad(memoryUsageList.get(i).getProcessCpuLoad());
			memoryUsagePayload.setSystemCpuLoad(memoryUsageList.get(i).getSystemCpuLoad());
			memoryUsagePayload.setProcessCpuTime(memoryUsageList.get(i).getProcessCpuTime());
			memoryUsagePayload.setCreationDate(memoryUsageList.get(i).getCreationDate());
			memoryUsagePayloadList.add(memoryUsagePayload);
			}

		}

		else if (rquest.getDurationTypeId() == 5) {
			Timestamp newdate = Timestamp.from(zonedDateTime.plus(-1, ChronoUnit.HOURS).toInstant());
			memoryUsageList = cpuUsageRepository.findByCreationdate(newdate);
			for(int i=0;i<memoryUsageList.size();i++) {
				CpuUsagePayload memoryUsagePayload = new CpuUsagePayload();
			memoryUsagePayload.setProcessCpuLoad(memoryUsageList.get(i).getProcessCpuLoad());
			memoryUsagePayload.setSystemCpuLoad(memoryUsageList.get(i).getSystemCpuLoad());
			memoryUsagePayload.setProcessCpuTime(memoryUsageList.get(i).getProcessCpuTime());
			memoryUsagePayload.setCreationDate(memoryUsageList.get(i).getCreationDate());
			memoryUsagePayloadList.add(memoryUsagePayload);
			}

		}

		else if (rquest.getDurationTypeId() == 6) {
			Timestamp newdate = Timestamp.from(zonedDateTime.plus(-7, ChronoUnit.HOURS).toInstant());
			memoryUsageList = cpuUsageRepository.findByCreationdate(newdate);

			for(int i=0;i<memoryUsageList.size();i++) {
				CpuUsagePayload memoryUsagePayload = new CpuUsagePayload();
			memoryUsagePayload.setProcessCpuLoad(memoryUsageList.get(i).getProcessCpuLoad());
			memoryUsagePayload.setSystemCpuLoad(memoryUsageList.get(i).getSystemCpuLoad());
			memoryUsagePayload.setProcessCpuTime(memoryUsageList.get(i).getProcessCpuTime());
			memoryUsagePayload.setCreationDate(memoryUsageList.get(i).getCreationDate());
			memoryUsagePayloadList.add(memoryUsagePayload);

		}
		}

		return memoryUsagePayloadList;
	}


	@Override
	public DeshboadCountApiResponse getAllCallhistoryCountbylocation() {

		DeshboadCountApiResponse response = new DeshboadCountApiResponse();
		List<Location> locationList = locationRepo.findAll();
		HashMap<Long, List<String>> locationmap = new HashMap<>();

		try {

			for (int i = 0; i < locationList.size(); i++) {
				List<String> ListofMobileNumber = new ArrayList<>();
				Long locationid = locationList.get(i).getId();
				List<User> userbylocationId = userRepo.getUserbylocationId(locationid);

				if (!userbylocationId.isEmpty()) {
					for (User user : userbylocationId) {
						String mobileNumber = user.getMobileNumber();
						ListofMobileNumber.add(mobileNumber);

					}
					locationmap.put(locationid, ListofMobileNumber);
				}
			}
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("locationmap", locationmap);

			RestTemplate restTemplate = new RestTemplate();
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.set("Authorization", asteriskApISecretKey);
			HttpEntity<String> entity = new HttpEntity<>(jsonObject.toString(), headers);

			final String uri = asteriskUrl + "callLogs/getAllCallCountByLocation";
			ResponseEntity<BasicResponsePayloadForLocation> result = restTemplate.exchange(uri, HttpMethod.POST, entity,
					BasicResponsePayloadForLocation.class);
			if (result.getBody().getCountBylocation().size() != 0) {
				HashMap<Long, Long> countBylocation = result.getBody().getCountBylocation();

				List<CalllogCountLocation> locationlist = new ArrayList<>();
				for (Map.Entry<Long, Long> e : countBylocation.entrySet()) {
					CalllogCountLocation location = new CalllogCountLocation();
					Long key = e.getKey();
					Location location2 = locationRepo.findById(key).get();
					location.setName(location2.getName());
					location.setLocationid(e.getKey());
					location.setCount(e.getValue());

					locationlist.add(location);

				}

				response.setCalllogCountByLocation(locationlist);

			} else {
				return response;
			}
		}

		catch (Exception e) {
			e.printStackTrace();
		}
		return response;

	}

	@Override
	public DeshboadCountApiResponseUnit getAllCallhistoryCountbyUnit() {

		DeshboadCountApiResponseUnit response = new DeshboadCountApiResponseUnit();
		List<Unit> locationList = unitRepository.findAll();
		HashMap<Long, List<String>> locationmap = new HashMap<>();

		try {

			for (int i = 0; i < locationList.size(); i++) {
				List<String> ListofMobileNumber = new ArrayList<>();
				Long locationid = locationList.get(i).getId();
				List<User> userbylocationId = userRepo.getUserbyUnitId(locationid);

				if (!userbylocationId.isEmpty()) {
					for (User user : userbylocationId) {
						String mobileNumber = user.getMobileNumber();
						ListofMobileNumber.add(mobileNumber);

					}
					locationmap.put(locationid, ListofMobileNumber);
				}
			}
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("locationmap", locationmap);

			RestTemplate restTemplate = new RestTemplate();
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.set("Authorization", asteriskApISecretKey);
			HttpEntity<String> entity = new HttpEntity<>(jsonObject.toString(), headers);

			final String uri = asteriskUrl + "callLogs/getAllCallCountByLocation";
			ResponseEntity<BasicResponsePayloadForLocation> result = restTemplate.exchange(uri, HttpMethod.POST, entity,
					BasicResponsePayloadForLocation.class);
			if (result.getBody().getCountBylocation().size() != 0) {
				HashMap<Long, Long> countBylocation = result.getBody().getCountBylocation();

				List<CalllogCountUnit> locationlist = new ArrayList<>();
				for (Map.Entry<Long, Long> e : countBylocation.entrySet()) {
					CalllogCountUnit location = new CalllogCountUnit();

					Long key = e.getKey();
					Unit location2 = unitRepository.findById(key).get();
					location.setName(location2.getName());

					location.setUnitid(e.getKey());
					location.setCount(e.getValue());

					locationlist.add(location);

				}

				response.setCalllogCountByUnit(locationlist);

			} else {
				return response;
			}
		}

		catch (Exception e) {
			e.printStackTrace();
		}
		return response;

	}

	@Override
	public DeshboadCountApiResponseDesignation getAllCallhistoryCountbyDesignation() {

		DeshboadCountApiResponseDesignation response = new DeshboadCountApiResponseDesignation();
		List<Designation> locationList = designationRepository.findAll();
		HashMap<Long, List<String>> locationmap = new HashMap<>();

		try {

			for (int i = 0; i < locationList.size(); i++) {
				List<String> ListofMobileNumber = new ArrayList<>();
				Long locationid = locationList.get(i).getId();
				List<User> userbylocationId = userRepo.getUserbyDesignationId(locationid);

				if (!userbylocationId.isEmpty()) {
					for (User user : userbylocationId) {
						String mobileNumber = user.getMobileNumber();
						ListofMobileNumber.add(mobileNumber);

					}
					locationmap.put(locationid, ListofMobileNumber);
				}
			}
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("locationmap", locationmap);

			RestTemplate restTemplate = new RestTemplate();
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.set("Authorization", asteriskApISecretKey);
			HttpEntity<String> entity = new HttpEntity<>(jsonObject.toString(), headers);

			final String uri = asteriskUrl + "callLogs/getAllCallCountByLocation";
			ResponseEntity<BasicResponsePayloadForLocation> result = restTemplate.exchange(uri, HttpMethod.POST, entity,
					BasicResponsePayloadForLocation.class);

			if (result.getBody().getCountBylocation().size() != 0) {
				HashMap<Long, Long> countBylocation = result.getBody().getCountBylocation();

				List<CalllogCountDesignation> locationlist = new ArrayList<>();
				for (Map.Entry<Long, Long> e : countBylocation.entrySet()) {
					CalllogCountDesignation location = new CalllogCountDesignation();
					Long key = e.getKey();
					Designation location2 = designationRepository.findById(key).get();
					location.setName(location2.getName());

					location.setDesignationid(e.getKey());
					location.setCount(e.getValue());

					locationlist.add(location);

				}

				response.setCalllogCountByDesignation(locationlist);

			} else {
				return response;
			}
		}

		catch (Exception e) {
			e.printStackTrace();
		}
		return response;

	}

	public static String convertTimeStampToStringDate(long timeStamp) {
		if (timeStamp != 0L) {
			Date currentDate = new Date(timeStamp);
			DateFormat df = new SimpleDateFormat("HH:mm:ss");
			String newDate = df.format(currentDate);

			return newDate;
		} else {
			return "";
		}
	}

}
