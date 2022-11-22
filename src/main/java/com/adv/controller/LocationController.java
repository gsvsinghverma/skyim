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
import com.adv.model.Location;
import com.adv.payloads.EmptyJsonBody;
import com.adv.payloads.apirequests.LocationSaveRequest;
import com.adv.payloads.apirequests.UpdateLocationRequest;
import com.adv.payloads.apiresponse.BasicApiResponse;
import com.adv.payloads.videomeeting.IdRequest;
import com.adv.service.LocationService;
import com.adv.util.Constant;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/location")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@SecurityRequirement(name = "Bearer Authentication")
public class LocationController {
	@Autowired
	private LocationService locationService;

	@PostMapping(value = "/save")
	public ResponseEntity<BasicApiResponse> addLocation(@RequestBody LocationSaveRequest location) {

		BasicApiResponse response = new BasicApiResponse();
		response.setData(new EmptyJsonBody());
		response.setStatus(false);

		try {
			Location locations = locationService.saveLocation(location);

			if (locations != null) {
				response.setStatus(true);
				response.setMessage("New Locations Added Successfully!");
				response.setData(locations);
			} else {
				response.setMessage("NewLocation Not Added!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.setMessage(Constant.ERROR_API_RESPONSE);
		}

		return ResponseEntity.ok(response);

	}

	@PostMapping(value = "/update")
	public ResponseEntity<BasicApiResponse> updateLocation(@RequestBody UpdateLocationRequest location) {

		BasicApiResponse response = new BasicApiResponse();
		response.setData(new EmptyJsonBody());
		response.setStatus(false);

		try {
			Location locations = locationService.updateLocation(location);

			if (locations != null) {
				response.setStatus(true);
				response.setMessage(" Location Update Successfully!!");
				response.setData(locations);
			} else {
				response.setMessage("Location Not Update!!");
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
			Location locations = locationService.get(request.getId());

			if (locations != null) {
				response.setStatus(true);
				response.setMessage("Locations Fetched Successfully!");
				response.setData(locations);
			} else {
				response.setMessage("Locations Not Found!");
			}

		} catch (Exception e) {
			e.printStackTrace();
			response.setMessage(Constant.ERROR_API_RESPONSE);
		}

		return ResponseEntity.ok(response);
	}

	@GetMapping("/getAll")
	public ResponseEntity<BasicApiResponse> getAllLocations() {

		BasicApiResponse response = new BasicApiResponse();
		response.setData(new EmptyJsonBody());
		response.setStatus(false);
		try {

			List<Location> locations = locationService.getAllLocation();

			if (locations != null && !locations.isEmpty()) {
				response.setStatus(true);
				response.setMessage("Location Details Fetched!");
				response.setData(locations);

			} else {

				response.setMessage("Location Details Fetched Failed!");
			}
		} catch (Exception e) {
			e.printStackTrace();

			response.setMessage(Constant.ERROR_API_RESPONSE);

		}

		return ResponseEntity.ok(response);
	}
	@DeleteMapping(value = "/delete")
	public ResponseEntity<BasicApiResponse> deleteLocations(@RequestBody IdRequest request) {
		BasicApiResponse response = new BasicApiResponse();

		response.setData(new EmptyJsonBody());
		response.setStatus(false);

		Location locations = locationService.get(request.getId());

		try {
			boolean status = locationService.deleteLocation(locations);

			if (status) {

				response.setStatus(true);
				response.setMessage("Location Deleted Successfully!");
			} else {

				response.setMessage("Location Not Found!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.setMessage(Constant.ERROR_API_RESPONSE);
		}
		return ResponseEntity.ok(response);
	}

}
