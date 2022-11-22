package com.adv.service;

import java.util.List;

import com.adv.model.Designation;
import com.adv.payloads.apirequests.DesignationSaveRequest;
import com.adv.payloads.apirequests.DesignationUpdateRequest;

public interface DesignationService {
	Designation saveDesignation(DesignationSaveRequest designation);
	Designation updateDesignation(DesignationUpdateRequest designation);

	Designation get(long id);

	List<Designation> getAllDesignation();


	boolean deleteDesignation(Designation designation);


}
