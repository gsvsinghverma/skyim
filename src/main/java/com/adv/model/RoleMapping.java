package com.adv.model;

import java.sql.Timestamp;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Null;

import com.adv.util.Constant;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "role_mapping")
public class RoleMapping {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@OneToOne(cascade = CascadeType.MERGE,orphanRemoval = true)
	@JoinColumn(name = "rolesidpk", referencedColumnName = "id")
    AdminRoles adminroles;

	@OneToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "module_id_pk", referencedColumnName = "id")
    Modules module;
	
	@OneToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "submodule_id_pk", referencedColumnName = "id")
    SubModule submodule;

	@Column(name = "isAssigned", columnDefinition = "boolean default false")
	boolean isAssigned;
	
	@JsonFormat(pattern = Constant.API_RESP_DATE_FORMATE)
	@Column(name = "CREATION_DATE")
	@Null
	@JsonIgnore
	private Timestamp creationDate;

	@JsonFormat(pattern = Constant.API_RESP_DATE_FORMATE)
	@Column(name = "UPDATION_DATE")
	@Null
	@JsonIgnore
	private Timestamp updatationDate;

	public Long getId() {
		return id;
	}
	
	public AdminRoles getAdminroles() {
		return adminroles;
	}

	public void setAdminroles(AdminRoles adminroles) {
		this.adminroles = adminroles;
	}


	public Modules getModule() {
		return module;
	}


	public void setModule(Modules module) {
		this.module = module;
	}

	public SubModule getSubmodule() {
		return submodule;
	}
	
	public void setSubmodule(SubModule submodule) {
		this.submodule = submodule;
	}

	public boolean isAssigned() {
		return isAssigned;
	}

	public void setAssigned(boolean isAssigned) {
		this.isAssigned = isAssigned;
	}

	public Timestamp getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Timestamp creationDate) {
		this.creationDate = creationDate;
	}
	
	
	
	
	

	

}
