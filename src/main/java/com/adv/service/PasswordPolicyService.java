package com.adv.service;

import java.util.List;

import com.adv.model.PasswordPolicy;
import com.adv.payloads.apirequests.SavePasswordPolicy;
import com.adv.payloads.apirequests.UpdatePasswordPolicy;

public interface PasswordPolicyService {
	
	PasswordPolicy savePasswordPolicy(SavePasswordPolicy policy);

	PasswordPolicy updatePasswordPolicy(UpdatePasswordPolicy policy);

	PasswordPolicy get(long id);

	List<PasswordPolicy> getAllPasswordPolicy();

	boolean deletePasswordPolicy(PasswordPolicy policy);

}
