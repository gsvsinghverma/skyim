package com.adv.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.adv.model.PasswordPolicy;
import com.adv.payloads.EmptyJsonBody;
import com.adv.payloads.apirequests.SavePasswordPolicy;
import com.adv.payloads.apirequests.UpdatePasswordPolicy;
import com.adv.payloads.apiresponse.BasicApiResponse;
import com.adv.payloads.videomeeting.IdRequest;
import com.adv.service.PasswordPolicyService;
import com.adv.util.Constant;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/passwordPolicy")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@SecurityRequirement(name = "Bearer Authentication")
public class PasswordPolicyController {
	@Autowired
	private PasswordPolicyService passwordPolicyService;

	@PostMapping(value = "/save")
	public ResponseEntity<BasicApiResponse> addPasswordPolicy(@Valid @RequestBody SavePasswordPolicy policy) {

		BasicApiResponse response = new BasicApiResponse();
		response.setData(new EmptyJsonBody());
		response.setStatus(false);

		try {
			PasswordPolicy passwordPolicy = passwordPolicyService.savePasswordPolicy(policy);

			if (passwordPolicy != null) {
				response.setStatus(true);
				response.setMessage("New PasswordPolicy Added Successfully!");
				response.setData(passwordPolicy);
			} else {
				response.setMessage("PasswordPolicy Not Added!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.setMessage(Constant.ERROR_API_RESPONSE);
		}

		return ResponseEntity.ok(response);

	}

	@PostMapping(value = "/update")
	public ResponseEntity<BasicApiResponse> updatePasswordPolicy(@Valid  @RequestBody UpdatePasswordPolicy policy) {

		BasicApiResponse response = new BasicApiResponse();
		response.setData(new EmptyJsonBody());
		response.setStatus(false);

		try {
			PasswordPolicy passwordPolicy = passwordPolicyService.updatePasswordPolicy(policy);

			if (passwordPolicy != null) {
				response.setStatus(true);
				response.setMessage(" PasswordPolicy Update Successfully!!");
				response.setData(passwordPolicy);
			} else {
				response.setMessage("PasswordPolicy Not Update!!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.setMessage(Constant.ERROR_API_RESPONSE);
		}

		return ResponseEntity.ok(response);

	}

	// view by id

	
	@PostMapping(value = "/viewById")
	public ResponseEntity<BasicApiResponse> viewById(@RequestBody IdRequest request) {

		BasicApiResponse response = new BasicApiResponse();
		response.setData(new EmptyJsonBody());
		response.setStatus(false);

		try {
			PasswordPolicy passwordPolicy = passwordPolicyService.get(request.getId());

			if (passwordPolicy != null) {
				response.setStatus(true);
				response.setMessage("PasswordPolicy Fetched Successfully!");
				response.setData(passwordPolicy);
			} else {
				response.setMessage("PasswordPolicy Not Found!");
			}

		} catch (Exception e) {
			e.printStackTrace();
			response.setMessage(Constant.ERROR_API_RESPONSE);
		}

		return ResponseEntity.ok(response);
	}

	// getall

	@GetMapping("/getAll")
	public ResponseEntity<BasicApiResponse> getAllPasswordPolicy() {

		BasicApiResponse response = new BasicApiResponse();
		response.setData(new EmptyJsonBody());
		response.setStatus(false);
		try {

			List<PasswordPolicy> passwordPolicy = passwordPolicyService.getAllPasswordPolicy();

			if (passwordPolicy != null && !passwordPolicy.isEmpty()) {
				response.setStatus(true);
				response.setMessage("PasswordPolicy Details Fetched!");
				response.setData(passwordPolicy);

			} else {

				response.setMessage("PasswordPolicy Details Fetched Failed!");
			}
		} catch (Exception e) {
			e.printStackTrace();

			response.setMessage(Constant.ERROR_API_RESPONSE);

		}

		return ResponseEntity.ok(response);
	}

	// delete

	@DeleteMapping(value = "/delete")
	public ResponseEntity<BasicApiResponse> deletePasswordPolicy(@RequestBody IdRequest request) {
		BasicApiResponse response = new BasicApiResponse();

		response.setData(new EmptyJsonBody());
		response.setStatus(false);

		PasswordPolicy passwordPolicy = passwordPolicyService.get(request.getId());

		try {
			boolean status = passwordPolicyService.deletePasswordPolicy(passwordPolicy);

			if (status) {

				response.setStatus(true);
				response.setMessage("PasswordPolicy Deleted Successfully!");
			} else {

				response.setMessage("PasswordPolicy Not Found!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.setMessage(Constant.ERROR_API_RESPONSE);
		}
		return ResponseEntity.ok(response);
	}

}
