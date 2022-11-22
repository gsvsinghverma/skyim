package com.adv.payloads.apiresponse;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CalllogCountLocation {
	
	private Long locationid;
	private String name;
	private Long count;

}
