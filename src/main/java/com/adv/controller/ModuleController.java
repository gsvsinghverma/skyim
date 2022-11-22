package com.adv.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.adv.model.Modules;
import com.adv.payloads.EmptyJsonBody;
import com.adv.payloads.apiresponse.BasicApiResponse;
import com.adv.service.ModulesService;
import com.adv.util.Constant;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/module")
@SecurityRequirement(name = "Bearer Authentication")
@Hidden
public class ModuleController {

	@Autowired
	private ModulesService modulesService;

	@GetMapping("/getAll")
	public ResponseEntity<BasicApiResponse> getAllModules() {

		BasicApiResponse response = new BasicApiResponse();
		response.setData(new EmptyJsonBody());
		response.setStatus(false);
		try {

			List<Modules> module = modulesService.fetchAll();

			if (module != null && !module.isEmpty()) {
				response.setStatus(true);
				response.setMessage("Module Details Received!");
				response.setData(module);

			} else {

				response.setMessage("Module Details Received Failed!");
			}
		} catch (Exception e) {
			e.printStackTrace();

			response.setMessage(Constant.ERROR_API_RESPONSE);

		}

		return ResponseEntity.ok(response);
	}

}
