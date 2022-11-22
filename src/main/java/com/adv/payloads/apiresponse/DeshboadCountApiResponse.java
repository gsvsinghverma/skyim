package com.adv.payloads.apiresponse;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeshboadCountApiResponse {
	
	List<CalllogCountLocation> calllogCountByLocation;

}
