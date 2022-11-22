package com.adv.service;

import java.util.List;

import com.adv.model.Location;
import com.adv.payloads.apirequests.LocationSaveRequest;
import com.adv.payloads.apirequests.UpdateLocationRequest;

public interface LocationService {
	Location saveLocation(LocationSaveRequest location);

	Location updateLocation(UpdateLocationRequest location);

	Location get(long id);

	List<Location> getAllLocation();

	boolean deleteLocation(Location location);

}
