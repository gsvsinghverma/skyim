package com.adv.serviceimpl;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adv.model.Location;
import com.adv.payloads.apirequests.LocationSaveRequest;
import com.adv.payloads.apirequests.UpdateLocationRequest;
import com.adv.repository.LocationRepository;
import com.adv.service.LocationService;

@Service
public class LocationServiceImpl implements LocationService {

	@Autowired
	private LocationRepository locationRepo;

	@Override
	public Location saveLocation(LocationSaveRequest location) {
		Location newLocation = new Location();
		newLocation.setName(location.getName().trim());
		newLocation.setCode(location.getCode());
		newLocation.setActive(true);

		Timestamp currentDT = new Timestamp(System.currentTimeMillis());
		newLocation.setCreationDate(currentDT);
		newLocation.setUpdationDate(currentDT);

		return locationRepo.save(newLocation);
	}

	@Override
	public Location updateLocation(UpdateLocationRequest location) {
		Location updateLocation = new Location();
		updateLocation.setId(location.getId());
		updateLocation.setName(location.getName());
		updateLocation.setCode(location.getCode());
		updateLocation.setActive(true);

		Timestamp currentDT = new Timestamp(System.currentTimeMillis());
		updateLocation.setCreationDate(currentDT);

		return locationRepo.save(updateLocation);
	}

	@Override
	public Location get(long id) {
		return locationRepo.findById(id).orElse(null);
	}

	@Override
	public List<Location> getAllLocation() {
		return locationRepo.findAll();
	}

	@Override
	public boolean deleteLocation(Location location) {
		try {
			locationRepo.delete(location);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}
