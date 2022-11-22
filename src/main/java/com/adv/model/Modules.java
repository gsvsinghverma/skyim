package com.adv.model;



import java.sql.Timestamp;
import java.util.List;

import javax.annotation.Nullable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.Null;

import com.adv.util.Constant;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;



@Entity
@Table(name = "modules")
public class Modules {
    
    



   public Modules() {
        super();
    }
    
    




    public Modules(Long id, String name, Timestamp creationDate, Timestamp updatationDate, boolean active) {
        super();
        this.id = id;
        this.name = name;
        this.creationDate = creationDate;
        this.updatationDate = updatationDate;
        this.active = active;
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;



   @Column(name = "NAME")
    private String name;
    @JsonFormat(pattern = Constant.API_RESP_DATE_FORMATE)
    @Column(name = "CREATION_DATE")
    @Null
    private Timestamp creationDate;



   @JsonIgnore
    @JsonFormat(pattern = Constant.API_RESP_DATE_FORMATE)
    @Column(name = "UPDATION_DATE")
    @Null
    private Timestamp updatationDate;
     
     @JsonIgnore
    @Column(name = "ACTIVE", columnDefinition = "boolean default true")
    private boolean active;
//   
     @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.MERGE)
       @JoinColumn(name = "submodulepk", referencedColumnName = "id")
     @Nullable
       List<SubModule> submodule;
    
    
    
    //@JoinColumn(name = "MODULE_ID", nullable = false)




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






    public List<SubModule> getSubmodule() {
        return submodule;
    }






    public void setSubmodule(List<SubModule> submodule) {
        this.submodule = submodule;
    }
    






}