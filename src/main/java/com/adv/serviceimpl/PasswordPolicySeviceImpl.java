package com.adv.serviceimpl;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adv.model.PasswordPolicy;
import com.adv.payloads.apirequests.SavePasswordPolicy;
import com.adv.payloads.apirequests.UpdatePasswordPolicy;
import com.adv.repository.PasswordPolicyRepository;
import com.adv.service.PasswordPolicyService;

@Service
public class PasswordPolicySeviceImpl implements PasswordPolicyService {

	@Autowired
	private PasswordPolicyRepository passwordPolicyRepo;

	@Override
	public PasswordPolicy savePasswordPolicy(@Valid SavePasswordPolicy policy) {
		PasswordPolicy newPolicy = new PasswordPolicy();

		newPolicy.setPasswordFor(policy.getPasswordFor());
		newPolicy.setPasswordDuration(policy.getPasswordDuration());
		newPolicy.setMinPasswordLength(policy.getMinPasswordLength());
		newPolicy.setMaxPasswordLength(policy.getMaxPasswordLength());

		Timestamp creationDate1 = new Timestamp(System.currentTimeMillis());
		newPolicy.setCreationDate(creationDate1);
		newPolicy.setUpdationDate(creationDate1);

		Timestamp ts = new Timestamp(System.currentTimeMillis());
		Calendar cal = Calendar.getInstance();
		cal.setTime(ts);
		cal.add(Calendar.DAY_OF_WEEK, policy.getPasswordDuration());
		ts.setTime(cal.getTime().getTime()); // or
		ts = new Timestamp(cal.getTime().getTime());
		newPolicy.setValidTill(ts);

		newPolicy.setSpecialCharacters(policy.getSpecialCharacters());

		newPolicy.setLastPasswordCheck(policy.isLastPasswordCheck());
		newPolicy.setWhiteSpaceCheck(policy.isWhiteSpaceCheck());
		newPolicy.setLowerCaseletters(policy.getLowerCaseletters());
		newPolicy.setUpperCaseletters(policy.getUpperCaseletters());
		newPolicy.setNumber(policy.getNumber());

		return passwordPolicyRepo.save(newPolicy);
	}

	@Override
	public PasswordPolicy updatePasswordPolicy(@Valid UpdatePasswordPolicy policy) {

		try {
			PasswordPolicy passwordOld = passwordPolicyRepo.findById(policy.getId()).orElse(null);
			if (passwordOld != null) {

				passwordOld.setPasswordDuration(policy.getPasswordDuration());
				passwordOld.setMinPasswordLength(policy.getMinPasswordLength());
				passwordOld.setMaxPasswordLength(policy.getMaxPasswordLength());

				Timestamp creationDate = new Timestamp(System.currentTimeMillis());
				passwordOld.setUpdationDate(creationDate);

				Timestamp ts = new Timestamp(System.currentTimeMillis());
				Calendar cal = Calendar.getInstance();
				cal.setTime(ts);
				cal.add(Calendar.DAY_OF_WEEK, policy.getPasswordDuration());
				ts.setTime(cal.getTime().getTime()); // or
				ts = new Timestamp(cal.getTime().getTime());
				passwordOld.setValidTill(ts);

				passwordOld.setSpecialCharacters(policy.getSpecialCharacters());
				passwordOld.setLastPasswordCheck(policy.isLastPasswordCheck());
				passwordOld.setWhiteSpaceCheck(policy.isWhiteSpaceCheck());
				passwordOld.setLowerCaseletters(policy.getLowerCaseletters());
				passwordOld.setUpperCaseletters(policy.getUpperCaseletters());
				passwordOld.setNumber(policy.getNumber());

				return passwordPolicyRepo.save(passwordOld);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public PasswordPolicy get(long id) {
		return passwordPolicyRepo.findById(id).orElse(null);
	}

	@Override
	public List<PasswordPolicy> getAllPasswordPolicy() {
		return passwordPolicyRepo.findAll();
	}

	@Override
	public boolean deletePasswordPolicy(PasswordPolicy policy) {
		try {
			passwordPolicyRepo.delete(policy);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

}
