package com.adv.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name="Configuration")
public class MailConfiguration {
	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;
	    
	    @Column(name = "parameters", columnDefinition = "varchar(150) default '' NOT NULL")
	    private String parameters;
	    
	    @Column(name = "parametervalues", columnDefinition = "varchar(150) default '' NOT NULL")
	    private String parametervalues;



	   public Long getId() {
	        return id;
	    }



	   public void setId(Long id) {
	        this.id = id;
	    }



	   public String getParameters() {
	        return parameters;
	    }



	   public void setParameters(String parameters) {
	        this.parameters = parameters;
	    }



	   public String getParametervalues() {
	        return parametervalues;
	    }



	   public void setParametervalues(String parametervalues) {
	        this.parametervalues = parametervalues;
	    }



	}