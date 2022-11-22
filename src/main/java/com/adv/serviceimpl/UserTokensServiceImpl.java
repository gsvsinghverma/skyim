package com.adv.serviceimpl;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adv.model.User;
import com.adv.model.UserTokens;
import com.adv.projections.DeviceTypeProjection;
import com.adv.repository.UserTokensRepository;
import com.adv.service.UserTokensService;

@Service
public class UserTokensServiceImpl implements UserTokensService {

	@Autowired
	private UserTokensRepository userTokensRepository;

	@Override
	public UserTokens add(User user, String token, String type) {
		UserTokens userTokenOld = userTokensRepository.findByUserUsernameAndToken(user.getUsername(), token);
		if (userTokenOld == null) {
			UserTokens userTokenNew = new UserTokens();
			userTokenNew.setToken(token);
			userTokenNew.setUser(user);
			Timestamp creationDate = new Timestamp(System.currentTimeMillis());
			userTokenNew.setCreationDate(creationDate);
			userTokenNew.setDeviceType(type);
			return userTokensRepository.save(userTokenNew);
		}
		return userTokenOld;
	}

	@Override
	public List<UserTokens> getUserListByUsername(String username) {
		return userTokensRepository.findByUserUsername(username);
	}

	@Override
	public int deleteTokens(String token) {
		List<UserTokens> tokens = userTokensRepository.findByToken(token);
		if (tokens != null || !tokens.isEmpty()) {
		
				userTokensRepository.deleteAll(tokens);
				return 1;
			
		}
		return 0;
	}

	@Override
	public List<DeviceTypeProjection> getDeviceTypeListByUsername(Long id) {
		return userTokensRepository.findDistinctDeviceTypeByUserId(id);
	}

	@Override
	public List<UserTokens> getUserListByUsernameAndDeviceType(String username, String deviceType) {
		return userTokensRepository.findByUserUsernameAndDeviceType(username, deviceType);
	}

	public List<UserTokens> getUserListByUserId(long orgId) {
		return userTokensRepository.findByUserId(orgId);
	}

}