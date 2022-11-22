package com.adv.payloads.apirequests;

import java.util.List;

import com.adv.payloads.ModuleSubModulePayload;

public class UpdateAdminRoleRequest {
	private Long id;
	
	private String name;
	
	private List<ModuleSubModulePayload> moduleSubmoduleMappings;
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<ModuleSubModulePayload> getModuleSubmoduleMappings() {
		return moduleSubmoduleMappings;
	}

	public void setModuleSubmoduleMappings(List<ModuleSubModulePayload> moduleSubmoduleMappings) {
		this.moduleSubmoduleMappings = moduleSubmoduleMappings;
	}

	
}
