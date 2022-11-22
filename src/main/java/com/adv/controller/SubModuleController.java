package com.adv.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.adv.model.SubModule;
import com.adv.payloads.EmptyJsonBody;
import com.adv.payloads.apirequests.SubModuleSaveRequest;
import com.adv.payloads.apirequests.UpdateSubModuleRequest;
import com.adv.payloads.apiresponse.BasicApiResponse;
import com.adv.payloads.videomeeting.IdRequest;
import com.adv.service.SubModuleService;
import com.adv.util.Constant;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/submodule")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@SecurityRequirement(name = "Bearer Authentication")
@Hidden
public class SubModuleController {
@Autowired
SubModuleService submoduleService;
	@PostMapping(value = "/save")
	public ResponseEntity<BasicApiResponse> saveSubmodule(@RequestBody SubModuleSaveRequest subModule) {

		BasicApiResponse response = new BasicApiResponse();
		response.setData(new EmptyJsonBody());
		response.setStatus(false);

		try {
			SubModule submodule = submoduleService.saveSubModule(subModule);

			if (submodule != null) {
				response.setStatus(true);
				response.setMessage("SubModules Save Successfully!");
				response.setData(submodule);
			} else {
				response.setMessage("SubModule Not Saved!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.setMessage(Constant.ERROR_API_RESPONSE);
		}

		return ResponseEntity.ok(response);

	}

	
	@PostMapping(value = "/update")
	public ResponseEntity<BasicApiResponse> updateSubmodule(@RequestBody UpdateSubModuleRequest subModule) {

		BasicApiResponse response = new BasicApiResponse();
		response.setData(new EmptyJsonBody());
		response.setStatus(false);

		try {
			SubModule submodule = submoduleService.updateSubModule(subModule);

			if (submodule != null) {
				response.setStatus(true);
				response.setMessage("SubModules Update Successfully!");
				response.setData(submodule);
			} else {
				response.setMessage("SubModule Not Update!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.setMessage(Constant.ERROR_API_RESPONSE);
		}

		return ResponseEntity.ok(response);

	}

	
	
	
	
	@DeleteMapping(value = "/delete")
	public ResponseEntity<BasicApiResponse> deleteSubmodule(@RequestBody IdRequest request) {

		BasicApiResponse response = new BasicApiResponse();
		response.setData(new EmptyJsonBody());
		response.setStatus(false);

		if (request.getId() == null || request.getId() == 0) {
			response.setMessage("ID can not be null or zero!");
			return ResponseEntity.ok(response);
		}

		SubModule submodul = submoduleService.get(request.getId());

		try {
			if (submodul != null) {
				boolean status = submoduleService.deleteSubModule(submodul);

				if (status) {
					response.setStatus(true);
					response.setMessage("Submodule Deleted Successfully!");
				} else {

					response.setMessage("Submodule Not Deleted!");
				}
			} else {

				response.setMessage("Submodule Not Found!");
			}

		} catch (Exception e) {
			e.printStackTrace();
			response.setMessage(Constant.ERROR_API_RESPONSE);
		}
		return ResponseEntity.ok(response);
	}


	@PostMapping(value = "/viewById")
	public ResponseEntity<BasicApiResponse> viewById(@RequestBody IdRequest request) {

		BasicApiResponse response = new BasicApiResponse();
		response.setData(new EmptyJsonBody());
		response.setStatus(false);

		if (request.getId() == null || request.getId() == 0) {
			response.setMessage("ID can not be null or zero!");
			return ResponseEntity.ok(response);
		}

		try {
			SubModule submodules = submoduleService.get(request.getId());
			
			if (submodules != null) {
				response.setStatus(true);
				response.setMessage("Submodule Fetched Successfully!");
				response.setData(submodules);
			} else {
				response.setMessage("Submodule Not Found!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.setMessage(Constant.ERROR_API_RESPONSE);

		}
		return ResponseEntity.ok(response);
	}

	
	
	
	@GetMapping("/getAll")
	public ResponseEntity<BasicApiResponse> getAllSubModules() {

		BasicApiResponse response = new BasicApiResponse();
		response.setData(new EmptyJsonBody());
		response.setStatus(false);
		try {

			List<SubModule> submodules = submoduleService.getAllSubModule();

			if (submodules != null && !submodules.isEmpty()) {
				response.setStatus(true);
				response.setMessage("SubModules Details Fetched!");
				response.setData(submodules);

			} else {

				response.setMessage("SubModules Details Fetched Failed!");
			}
		} catch (Exception e) {
			e.printStackTrace();

			response.setMessage(Constant.ERROR_API_RESPONSE);

		}

		return ResponseEntity.ok(response);
	}	
	
	
	
	
}
