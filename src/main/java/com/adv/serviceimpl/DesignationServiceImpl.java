package com.adv.serviceimpl;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adv.model.Designation;
import com.adv.payloads.apirequests.DesignationSaveRequest;
import com.adv.payloads.apirequests.DesignationUpdateRequest;
import com.adv.repository.DesignationRepository;
import com.adv.service.DesignationService;

@Service
public class DesignationServiceImpl implements DesignationService {
	@Autowired
	DesignationRepository designatinrepository;

	@Override
	public Designation saveDesignation(DesignationSaveRequest designation) {
		
		
		Designation designations= new Designation();
		designations.setName(designation.getName());
		designations.setCode(designation.getCode());
		
		designations.setActive(true);
		Timestamp currentDT = new Timestamp(System.currentTimeMillis());
		designations.setCreationDate(currentDT);
		designations.setUpdationDate(currentDT);
designations.setCreationDate(currentDT);
designations.setUpdationDate(currentDT);

		
		
		
		return designatinrepository.save(designations);
	}

	@Override
	public Designation updateDesignation(DesignationUpdateRequest designation) {

		Designation designations= new Designation();
		designations.setId(designation.getId());
		designations.setName(designation.getName());
		designations.setCode(designation.getCode());
		
		designations.setActive(true);
		Timestamp currentDT = new Timestamp(System.currentTimeMillis());
		designations.setCreationDate(currentDT);
		designations.setUpdationDate(currentDT);
designations.setCreationDate(currentDT);
designations.setUpdationDate(currentDT);
		return designatinrepository.save(designations);
	}

	@Override
	public Designation get(long id) {
		return designatinrepository.findById(id).orElse(null);
	}

	@Override
	public List<Designation> getAllDesignation() {

		return designatinrepository.findAll();
	}

	@Override
	public boolean deleteDesignation(Designation designation) {
try {
	designatinrepository.delete(designation);
	return true;
	
} catch (Exception e) {
	e.printStackTrace();

}
		return false;
	}


}
