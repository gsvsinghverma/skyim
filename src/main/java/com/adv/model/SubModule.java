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
@Table(name = "sub_module")
public class SubModule {



   @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;



   @Column(name = "NAME")
    private String name;
    
    @Column(name = "status", columnDefinition = "boolean default true")
    private boolean status;



   @JsonFormat(pattern = Constant.API_RESP_DATE_FORMATE)
    @Column(name = "CREATION_DATE")
    private Timestamp creationDate;



   @JsonFormat(pattern = Constant.API_RESP_DATE_FORMATE)
    @Column(name = "UPDATION_DATE")
    private Timestamp updatationDate;
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



   public boolean isStatus() {
        return status;
    }



   public void setStatus(boolean status) {
        this.status = status;
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



   public Timestamp getUpdatationDate() {
        return updatationDate;
    }



   public void setUpdatationDate(Timestamp updatationDate) {
        this.updatationDate = updatationDate;
    }



   public boolean isActive() {
        return active;
        }



   public void setActive(boolean active) {
        this.active = active;
    }
    

    
}