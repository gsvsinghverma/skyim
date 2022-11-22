package com.adv.model;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Null;

import com.adv.util.Constant;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "module_submodule_mapping")
public class ModuleSubmoduleMapping {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "module_id", referencedColumnName = "id")
	List<Modules> module=new ArrayList<Modules>();

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

	public void setId(Long id) {
		this.id = id;
	}

	

	public List<Modules> getModule() {
		return module;
	}

	public void setModule(List<Modules> module) {
		this.module = module;
	}

}
