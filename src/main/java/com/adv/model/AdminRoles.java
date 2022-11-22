package com.adv.model;



import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import com.adv.util.Constant;
import com.fasterxml.jackson.annotation.JsonFormat;
@Entity
@Table(name = "admin_roles")
public class AdminRoles {



   public boolean isStatus() {
		return status;
	}



	public void setStatus(boolean status) {
		this.status = status;
	}

@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;



   @Column(name = "NAME", columnDefinition = "varchar(150) default '' NOT NULL")
    private String name;



   @JsonFormat(pattern = Constant.API_RESP_DATE_FORMATE)
    @Column(name = "CREATION_DATE")
    private Timestamp creationDate;


    @JsonFormat(pattern = Constant.API_RESP_DATE_FORMATE)
    @Column(name = "UPDATION_DATE")
    private Timestamp updationDate;
    
    @Column(name = "status", columnDefinition = "boolean default true")// for enable disable
    private boolean status;
    
    @Column(name = "ismasterrole", columnDefinition = "boolean default false")// for enable disable
    private boolean ismasterrole;
    



   @Column(name = "ACTIVE", columnDefinition = "boolean default true")
    private boolean active;


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
    


   public boolean isIsmasterrole() {
        return ismasterrole;
    }



   public void setIsmasterrole(boolean ismasterrole) {
        this.ismasterrole = ismasterrole;
    }



   



   public AdminRoles() {



   }



}