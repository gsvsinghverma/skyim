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

import com.adv.model.Unit;
import com.adv.payloads.EmptyJsonBody;
import com.adv.payloads.apirequests.SaveUnitRequest;
import com.adv.payloads.apirequests.UpdateUnitsRequest;
import com.adv.payloads.apiresponse.BasicApiResponse;
import com.adv.payloads.videomeeting.IdRequest;
import com.adv.service.UnitService;
import com.adv.util.Constant;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/unit")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@SecurityRequirement(name = "Bearer Authentication")
public class UnitController {

	@Autowired
	UnitService unitService;


	@PostMapping(value = "/save")
	public ResponseEntity<BasicApiResponse> saveUnits(@RequestBody SaveUnitRequest units) {
		BasicApiResponse response = new BasicApiResponse();
		response.setData(new EmptyJsonBody());
		response.setStatus(false);
		try {
			Unit newunit = unitService.saveUnits(units);
			if (newunit != null) {
				response.setStatus(true);
				response.setMessage("Unit Saved Successfuly!");
				response.setData(newunit);
			} else {
				response.setMessage(" Unit is Not Saved!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.setMessage(Constant.ERROR_API_RESPONSE);
		}
		return ResponseEntity.ok(response);
	}

	@PostMapping(value = "/update")
	public ResponseEntity<BasicApiResponse> updateLocation(@RequestBody UpdateUnitsRequest unit) {

		BasicApiResponse response = new BasicApiResponse();
		response.setData(new EmptyJsonBody());
		response.setStatus(false);

		try {
			Unit unitss = unitService.updateUnits(unit);

			if (unitss != null) {
				response.setStatus(true);
				response.setMessage(" Unit Update Successfully!!");
				response.setData(unitss);
			} else {
				response.setMessage("Unit Not Update!!");
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
			Unit units = unitService.get(request.getId());

			if (units != null) {
				response.setStatus(true);
				response.setMessage("Units Fetched Successfully!");
				response.setData(units);
			} else {
				response.setMessage("Units Not Found!");
			}

		} catch (Exception e) {
			e.printStackTrace();
			response.setMessage(Constant.ERROR_API_RESPONSE);
		}

		return ResponseEntity.ok(response);
	}

	@GetMapping("/getAll")
	public ResponseEntity<BasicApiResponse> getAllUnits() {

		BasicApiResponse response = new BasicApiResponse();
		response.setData(new EmptyJsonBody());
		response.setStatus(false);
		try {

			List<Unit> units = unitService.getAllUnits();

			if (units != null && !units.isEmpty()) {
				response.setStatus(true);
				response.setMessage("Units Details Fetched!");
				response.setData(units);

			} else {

				response.setMessage("Units Details Fetched Failed!");
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

		Unit units = unitService.get(request.getId());

		try {
			boolean status = unitService.deleteUnits(units);

			if (status) {

				response.setStatus(true);
				response.setMessage("Unit Deleted Successfully!");
			} else {

				response.setMessage("Unit Not Found!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.setMessage(Constant.ERROR_API_RESPONSE);
		}
		return ResponseEntity.ok(response);
	}

}
