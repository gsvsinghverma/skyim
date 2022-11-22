package com.adv.payloads.apirequests;

import java.util.List;


public class AdminRoleSaveRequest {

	private String name;
	
	private List<Long> moduleIds;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Long> getModuleIds() {
		return moduleIds;
	}

	public void setModuleIds(List<Long> moduleIds) {
		this.moduleIds = moduleIds;
	}
	
	

}
