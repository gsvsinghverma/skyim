package com.adv.model;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="memoryusage")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MemoryUsage {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private Long FreePhysicalMemorySize;
	private Long TotalPhysicalMemorySize;
	private Long UsagePhysicalMemorySize;
	

	private Timestamp creationDate;


	

}
