package com.adv.payloads.apiresponse;

public class CheckExtenExistOrNotRequest {
	private String exten ;
	private String callQueueId;
	public String getExten() {
		return exten;
	}
	public void setExten(String exten) {
		this.exten = exten;
	}
	public String getCallQueueId() {
		return callQueueId;
	}
	public void setCallQueueId(String callQueueId) {
		this.callQueueId = callQueueId;
	}	
	
}
