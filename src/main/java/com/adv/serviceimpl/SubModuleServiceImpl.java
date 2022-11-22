package com.adv.serviceimpl;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adv.model.SubModule;
import com.adv.payloads.apirequests.SubModuleSaveRequest;
import com.adv.payloads.apirequests.UpdateSubModuleRequest;
import com.adv.repository.SubModuleRepository;
import com.adv.service.SubModuleService;
@Service
public class SubModuleServiceImpl implements SubModuleService {
@Autowired
SubModuleRepository submoduleRepo;
	@Override
	public SubModule saveSubModule(SubModuleSaveRequest submodule) {
		
	
		
		
		
			SubModule newSubmodule = new SubModule();
			
			newSubmodule.setId(submodule.getId());
			newSubmodule.setName(submodule.getName().trim());
			
			newSubmodule.setActive(true);

			Timestamp currentDT = new Timestamp(System.currentTimeMillis());
			newSubmodule.setCreationDate(currentDT);
			newSubmodule.setUpdatationDate(currentDT);

			return submoduleRepo.save(newSubmodule);
		}


		
		
		
		
		
		
		
	

	@Override
	public SubModule updateSubModule(UpdateSubModuleRequest submodule) {
		SubModule newSubmodule = new SubModule();
			
			newSubmodule.setId(submodule.getId());
			newSubmodule.setName(submodule.getName().trim());
			
			newSubmodule.setActive(true);

			Timestamp currentDT = new Timestamp(System.currentTimeMillis());
			newSubmodule.setCreationDate(currentDT);
			newSubmodule.setUpdatationDate(currentDT);

			return submoduleRepo.save(newSubmodule);
		}


	

	@Override
	public SubModule get(long id) {
		return submoduleRepo.findById(id).orElse(null);
		
	}

	
	
	@Override
	public List<SubModule> getAllSubModule() {
		return submoduleRepo.findAll();
	}

	
	
	
	@Override
	public boolean deleteSubModule(SubModule submodule) {
		try {
			submoduleRepo.delete(submodule);
			return true;
			
		} catch (Exception e) {
			return false;
		}
		
		
	}


	@Override
	public SubModule get(Long attribute) {
		return submoduleRepo.findById(attribute).orElse(null);
	}

}
