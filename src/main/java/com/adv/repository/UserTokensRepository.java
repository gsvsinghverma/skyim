package com.adv.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.adv.model.UserTokens;
import com.adv.projections.DeviceTypeProjection;

public interface UserTokensRepository extends JpaRepository<UserTokens, Long> {

	UserTokens findByUserUsernameAndToken(String username, String token);

	List<UserTokens> findByUserUsername(String username);

	List<UserTokens> findByUserUsernameAndDeviceType(String username, String deviceType);

	List<UserTokens> findByToken(String token);

	List<DeviceTypeProjection> findDistinctDeviceTypeByUserId(Long id);

	List<UserTokens> findByUserId(long orgId);

}