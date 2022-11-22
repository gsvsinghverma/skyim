package com.adv.serviceimpl;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adv.model.Unit;
import com.adv.payloads.apirequests.SaveUnitRequest;
import com.adv.payloads.apirequests.UpdateUnitsRequest;
import com.adv.repository.UnitRepository;
import com.adv.service.UnitService;

@Service
public class UnitServiceImpl implements UnitService {
	@Autowired
	UnitRepository unitRepository;

	@Override
	public Unit saveUnits(SaveUnitRequest units) {
		Unit newUnit = new Unit();

		newUnit.setName(units.getName());
		newUnit.setCode(units.getCode());
		newUnit.setActive(true);
		Timestamp currentDT = new Timestamp(System.currentTimeMillis());
		newUnit.setCreationDate(currentDT);
		newUnit.setUpdationDate(currentDT);

		return unitRepository.save(newUnit);
	}

	@Override
	public Unit updateUnits(UpdateUnitsRequest unit) {
		Unit updateUnit = new Unit();
		updateUnit.setId(unit.getId());
		updateUnit.setName(unit.getName());
		updateUnit.setCode(unit.getCode());
		updateUnit.setActive(true);
		Timestamp currentDT = new Timestamp(System.currentTimeMillis());
		updateUnit.setCreationDate(currentDT);
		updateUnit.setUpdationDate(currentDT);

		return unitRepository.save(updateUnit);

	}

	@Override
	public Unit get(long id) {

		return unitRepository.findById(id).orElse(null);
	}

	@Override
	public List<Unit> getAllUnits() {
		return unitRepository.findAll();
	}

	@Override
	public boolean deleteUnits(Unit unit) {
		try {
			unitRepository.delete(unit);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;

	}

}
