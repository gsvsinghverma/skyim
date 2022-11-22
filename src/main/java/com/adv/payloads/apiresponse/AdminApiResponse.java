package com.adv.payloads.apiresponse;

import java.util.List;

import com.adv.model.Designation;
import com.adv.model.Location;
import com.adv.model.RoleMapping;
import com.adv.model.Unit;

public class AdminApiResponse {
	private String username;
	private String firstName;
	public List<RoleMapping> getRolemappingdetails() {
		return rolemappingdetails;
	}
	public void setRolemappingdetails(List<RoleMapping> rolemappingdetails) {
		this.rolemappingdetails = rolemappingdetails;
	}
	private String lastName;
	private boolean active;
	private String profilePhoto;
	private String mobileNumber;
	private String email;
	private Designation designation;
	private Location location;
	private Unit unit;
	private List<RoleMapping> rolemappingdetails;
	private boolean ismasteradmin;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	public String getProfilePhoto() {
		return profilePhoto;
	}
	public void setProfilePhoto(String profilePhoto) {
		this.profilePhoto = profilePhoto;
	}
	public String getMobileNumber() {
		return mobileNumber;
	}
	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Designation getDesignation() {
		return designation;
	}
	public void setDesignation(Designation designation) {
		this.designation = designation;
	}
	public Location getLocation() {
		return location;
	}
	public void setLocation(Location location) {
		this.location = location;
	}
	public Unit getUnit() {
		return unit;
	}
	public void setUnit(Unit unit) {
		this.unit = unit;
	}
	
	public boolean isIsmasteradmin() {
		return ismasteradmin;
	}
	public void setIsmasteradmin(boolean ismasteradmin) {
		this.ismasteradmin = ismasteradmin;
	}	
	
}
