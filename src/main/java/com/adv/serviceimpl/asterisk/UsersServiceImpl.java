package com.adv.serviceimpl.asterisk;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.adv.service.asterisk.UsersService;

@Service
public class UsersServiceImpl implements UsersService {

	@Value("${asteriskUrl}")
	private String asteriskUrl;

	@Value("${asteriskApISecretKey}")
	private String asteriskApISecretKey;

	@Override
	public long countByExtensionAndPassword(String extension, String password) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("username", extension);
		jsonObject.put("password", password);

		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", asteriskApISecretKey);
		HttpEntity<String> entity = new HttpEntity<>(jsonObject.toString(), headers);

		final String uri = asteriskUrl + "user/countByExtensionAndPassword";
		ResponseEntity<Long> result = restTemplate.exchange(uri, HttpMethod.POST, entity, Long.class);
		return result.getBody();
	}

	@Override
	public long countByExtension(String extension) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("username", extension);

		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", asteriskApISecretKey);
		HttpEntity<String> entity = new HttpEntity<>(jsonObject.toString(), headers);

		final String uri = asteriskUrl + "user/countByExtension";
		ResponseEntity<Long> result = restTemplate.exchange(uri, HttpMethod.POST, entity, Long.class);
		return result.getBody();
	}
}