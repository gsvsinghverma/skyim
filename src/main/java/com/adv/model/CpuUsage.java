package com.adv.model;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="cpuusage")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CpuUsage {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@JsonFormat(pattern="HH:mm:ss")
	private String processCpuTime;
	private String processCpuLoad;
	private String systemCpuLoad;
	private Timestamp creationDate;


	

}
