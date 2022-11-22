package com.adv.service.asterisk;

public interface UsersService {

	public long countByExtensionAndPassword(String extension, String password);
	
	public long countByExtension(String extension);
}