package com.adv.service;

import java.util.List;

import com.adv.model.Unit;
import com.adv.payloads.apirequests.SaveUnitRequest;
import com.adv.payloads.apirequests.UpdateUnitsRequest;

public interface UnitService {
	Unit saveUnits(SaveUnitRequest units);
	Unit updateUnits(UpdateUnitsRequest unit);

	Unit get(long id);

	List<Unit> getAllUnits();






	boolean deleteUnits(Unit unit);



}
