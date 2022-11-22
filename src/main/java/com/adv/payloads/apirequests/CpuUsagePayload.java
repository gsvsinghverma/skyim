package com.adv.payloads.apirequests;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CpuUsagePayload {
	
	private String processCpuLoad;
	@JsonFormat(pattern="HH:mm:ss")
	private String processCpuTime;
	private String systemCpuLoad;
	private Timestamp creationDate;
	
}
