package com.adv.service;

import java.util.List;

import com.adv.model.User;
import com.adv.model.UserTokens;
import com.adv.projections.DeviceTypeProjection;

public interface UserTokensService {

	UserTokens add(User user, String token, String type);

	List<UserTokens> getUserListByUsername(String username);

	int deleteTokens(String token);

	List<DeviceTypeProjection> getDeviceTypeListByUsername(Long id);


	List<UserTokens> getUserListByUsernameAndDeviceType(String username, String deviceType);

	List<UserTokens> getUserListByUserId(long orgId);

}