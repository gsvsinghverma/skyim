package com.adv;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.adv.repository.MemoryUsageRepository;
import com.adv.service.AdminService;
import com.adv.service.MemoryUsageDurationService;
import com.adv.util.Constant;

@Component
public class CmdLineAppStartupRunner implements CommandLineRunner {


	@Autowired
	AdminService adminService;
	
	@Autowired
	MemoryUsageDurationService durationService;
	
	@Autowired
	MemoryUsageRepository memoryUsageRepository;

	@Override
	public void run(String... args) throws Exception {

		

       adminService.saveAdminDetails(Constant.ADMIN_USER_NAME);

       adminService.savememoryUsageFirstTime();
       adminService.savemecpuUsageFirstTime();
       durationService.saveMemoryUsageDuration();


	}
}