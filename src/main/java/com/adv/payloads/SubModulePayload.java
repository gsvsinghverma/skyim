package com.adv.payloads;

import java.util.List;

public class SubModulePayload {
	private List<Long> id;
	private boolean status;
	private boolean active;
	
	public List<Long> getId() {
		return id;
	}
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
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
	
}
