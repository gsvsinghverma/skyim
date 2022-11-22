package com.adv.payloads;

import java.util.List;

public class ModulePayload {
	private List<Long> id;
	private boolean active;
	
	private SubModulePayload submodulepayload;
	
	public List<Long> getId() {
		return id;
	}
	public void setId(List<Long> id) {
		this.id = id;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	public SubModulePayload getSubmodulepayload() {
		return submodulepayload;
	}
	public void setSubmodulepayload(SubModulePayload submodulepayload) {
		this.submodulepayload = submodulepayload;
	}
	
	
}
