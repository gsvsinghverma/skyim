package com.adv.payloads.apirequests;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemoryUsagePayload {
	
	
	private Long FreePhysicalMemorySize;
	private Long TotalPhysicalMemorySize;
	private Long UsagePhysicalMemorySize;
	private Timestamp creationDate;


}
