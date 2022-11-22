package com.adv.serviceimpl;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.adv.projections.PasswordProjection;
import com.adv.repository.AdminRepository;
import com.adv.repository.UserRepository;

@Service
@Primary
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private AdminRepository adminRepository;

	@Autowired
	PasswordEncoder encoder;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		PasswordProjection user = userRepository.findByLoginUsernameAndActive(username, true);
		if (user != null && user.getPassword() != null) {
			return new org.springframework.security.core.userdetails.User(username, encoder.encode(user.getPassword()),
					new ArrayList<>());
		} else {
					return null;
		}
	}

	public UserDetails loadUserByAdminname(String username) throws UsernameNotFoundException {

		PasswordProjection user = adminRepository.findByUsernameAndActive(username, true);
		if (user != null && user.getPassword() != null) {
			return new org.springframework.security.core.userdetails.User(username, encoder.encode(user.getPassword()),
					new ArrayList<>());
		} else {
			throw new UsernameNotFoundException("User not found with username: " + username);
		}
	}

	public void authenticate(String username, String password) throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}
	}
}