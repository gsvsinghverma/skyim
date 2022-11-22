package com.adv.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adv.model.MemoryUsageDuration;
import com.adv.repository.MemoryUsageDurationRepository;
import com.adv.service.MemoryUsageDurationService;

@Service
public class MemoryUsageDurationServiceImpl implements MemoryUsageDurationService {

	@Autowired
	private MemoryUsageDurationRepository locationRepo;
	
	

	 @Override
		public  void saveMemoryUsageDuration() {
		
			 List<MemoryUsageDuration> meetingRecurrence = locationRepo.findAll();
			 if(meetingRecurrence.isEmpty()) {
				 locationRepo.insertMemoryUsageDuration();
			 }
			

			    }

	

}
