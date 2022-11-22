package com.adv.util;

import java.util.ArrayList;
import java.util.List;

import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.passay.LengthRule;
import org.passay.PasswordData;
import org.passay.PasswordValidator;
import org.passay.Rule;
import org.passay.RuleResult;
import org.passay.WhitespaceRule;

import com.adv.model.PasswordPolicy;
import com.adv.payloads.apiresponse.BasicApiResponse;

public class PasswordPolicyUtil2 {

	public PasswordPolicyUtil2() {
		super();
	}

	public static BasicApiResponse passwordPolicyCheck(PasswordPolicy passwordPolicy, String password) {

		BasicApiResponse response = new BasicApiResponse();
		List<Rule> rules = new ArrayList<>();

		rules.add(new LengthRule(passwordPolicy.getMinPasswordLength(), passwordPolicy.getMaxPasswordLength()));

		rules.add(new WhitespaceRule());

		rules.add(new CharacterRule(EnglishCharacterData.UpperCase, passwordPolicy.getUpperCaseletters()));

		rules.add(new CharacterRule(EnglishCharacterData.LowerCase, passwordPolicy.getLowerCaseletters()));

		rules.add(new CharacterRule(EnglishCharacterData.Digit, passwordPolicy.getNumber()));

		rules.add(new CharacterRule(EnglishCharacterData.Special, passwordPolicy.getSpecialCharacters()));

		PasswordValidator validator = new PasswordValidator(rules);
		PasswordData data = new PasswordData(password);
		RuleResult result = validator.validate(data);

		if (result.isValid()) {
			response.setMessage("password is valid");
			response.setStatus(true);
		} else {
			response.setMessage(
					"Invalid Password: " + validator.getMessages(result).toString().replace("]", "").replace("[", ""));
			response.setStatus(false);
		}
		return response;

	}
}