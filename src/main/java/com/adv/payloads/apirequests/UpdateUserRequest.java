package com.adv.payloads.apirequests;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateUserRequest {
	private Long id;

	private String username;
	private String mobileNumber;
	private String emailId;
	private String displayName;
	private String loginUsername;
	private boolean otpEnable;
	private int otpType;
	private String password;
	private String firstName;
	private String lastName;
	private int passwordStatus;

	private boolean dndStatus;
	private boolean verificationStatus;
	private boolean callForwardingStatus;
	private boolean callRecordingStatus;
	private String virtualNumber;
	private String outboundCid;
	private String facebookId;
	private String googleId;
	private String profilePhoto;
	
	private String customValue;
}
