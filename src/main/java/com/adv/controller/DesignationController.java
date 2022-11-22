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

import com.adv.model.Designation;
import com.adv.payloads.EmptyJsonBody;
import com.adv.payloads.apirequests.DesignationSaveRequest;
import com.adv.payloads.apirequests.DesignationUpdateRequest;
import com.adv.payloads.apiresponse.BasicApiResponse;
import com.adv.payloads.videomeeting.IdRequest;
import com.adv.service.DesignationService;
import com.adv.util.Constant;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
@RestController
@RequestMapping("/designation")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@SecurityRequirement(name = "Bearer Authentication")
public class DesignationController {
	@Autowired
	DesignationService designationService;

	@GetMapping(value = "/getAll")
	public ResponseEntity<BasicApiResponse> getDesignation() {
		BasicApiResponse response = new BasicApiResponse();
		response.setData(new EmptyJsonBody());
		response.setStatus(false);
		List<Designation> designation = designationService.getAllDesignation();
		try {

			if (designation != null && !designation.isEmpty()) {

				response.setData(designation);

				response.setStatus(true);
				response.setMessage("Designation Fetched Successffully!");

			} else {

				response.setMessage("Designation Not Found!");
			}
		} catch (

		Exception e) {
			e.printStackTrace();
			response.setMessage(Constant.ERROR_API_RESPONSE);
		}
		return ResponseEntity.ok(response);
	}

	@PostMapping(value = "/save")
	public ResponseEntity<BasicApiResponse> addDesignation(@RequestBody DesignationSaveRequest designation)
	{

		BasicApiResponse response = new BasicApiResponse();
		response.setData(new EmptyJsonBody());
		response.setStatus(false);
		try {
			Designation newdesignation = designationService.saveDesignation(designation);

			if (newdesignation != null) {
				response.setStatus(true);
				response.setMessage("   Designation Saved Successfuly!");
				response.setData(newdesignation);
			} else {
				response.setMessage("  Designation is Not Saved!");
			}
		} catch (

		Exception e) {
			e.printStackTrace();
			response.setMessage(Constant.ERROR_API_RESPONSE);
		}
		return ResponseEntity.ok(response);
	}

	
	@PostMapping(value = "/Update")
	public ResponseEntity<BasicApiResponse> updateDesignationById(@RequestBody
			DesignationUpdateRequest designation) {

		BasicApiResponse response = new BasicApiResponse();
		response.setData(new EmptyJsonBody());
		response.setStatus(false);
		try {
			Designation newdesignation = designationService.updateDesignation(designation);
			if (newdesignation != null) {
				response.setStatus(true);
				response.setMessage("  Designation  Update Successfuly!");
				response.setData(newdesignation);
			} else {
				response.setMessage(" Designation is Not Updated!");
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

		try {
			Designation designations = designationService.get(request.getId());

			if (designations != null) {
				response.setStatus(true);
				response.setMessage("Designations Fetched Successfully!");
				response.setData(designations);
			} else {
				response.setMessage("Designations Not Found!");
			}

		} catch (Exception e) {
			e.printStackTrace();
			response.setMessage(Constant.ERROR_API_RESPONSE);
		}

		return ResponseEntity.ok(response);
	}


	
	
	
	
	
	
	@DeleteMapping(value = "/delete")
	public ResponseEntity<BasicApiResponse> deleteUnits(@RequestBody IdRequest request) {
		BasicApiResponse response = new BasicApiResponse();

		response.setData(new EmptyJsonBody());
		response.setStatus(false);

		Designation designations = designationService.get(request.getId());
		

		try {
			boolean status = designationService.deleteDesignation(designations);

			if (status) {

				response.setStatus(true);
				response.setMessage("Designation Deleted Successfully!");
			} else {

				response.setMessage("Designation Not Found!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.setMessage(Constant.ERROR_API_RESPONSE);
		}
		return ResponseEntity.ok(response);
	}


}
