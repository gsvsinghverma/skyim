package com.adv.projections;

import com.fasterxml.jackson.annotation.JsonFormat;

public interface ProjectionVoiceMails {

	String getSipUserName();

	String getVoiceMailPath();

	String getVoiceMailText();

	String getLength();

	@JsonFormat(shape = JsonFormat.Shape.NUMBER)
	String getVoiceMailDate();

	String getUserMobileNumber();

	String getEmailFrom();

}