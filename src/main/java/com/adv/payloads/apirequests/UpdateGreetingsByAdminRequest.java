package com.adv.payloads.apirequests;

import java.sql.Timestamp;


public class UpdateGreetingsByAdminRequest {
	private Long id;
	private String description;
	private String filePath;
	private String recordingName;
	private Timestamp creationDate;
	private Timestamp updationDate;
	private boolean active;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public String getRecordingName() {
		return recordingName;
	}
	public void setRecordingName(String recordingName) {
		this.recordingName = recordingName;
	}
	public Timestamp getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Timestamp creationDate) {
		this.creationDate = creationDate;
	}
	public Timestamp getUpdationDate() {
		return updationDate;
	}
	public void setUpdationDate(Timestamp updationDate) {
		this.updationDate = updationDate;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}


}
