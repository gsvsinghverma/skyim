package com.adv.configuration;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.adv.model.InvalidationLogin;
import com.adv.payloads.apiresponse.BasicApiResponse;
import com.adv.repository.InvalidationLoginRepository;
import com.adv.serviceimpl.UserDetailsServiceImpl;
import com.adv.util.Constant;
import com.adv.util.InvalidateLoginResponse;

import io.jsonwebtoken.ExpiredJwtException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

	@Autowired
	private UserDetailsServiceImpl userDetailsService;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	
	@Autowired
	InvalidationLoginRepository invalidationLoginRepository;
	

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws ServletException, IOException {

		final String requestTokenHeader = request.getHeader("Authorization");
		BasicApiResponse basicApiResponse=new BasicApiResponse();

		String username = null;
		String jwtToken = null;
     
		if (requestTokenHeader != null && !requestTokenHeader.isEmpty() && requestTokenHeader.startsWith("Bearer ")) {
			jwtToken = requestTokenHeader.substring(7);
			try {
				
				username = jwtTokenUtil.getUsernameFromToken(jwtToken);
				if(username!=null) {
					InvalidateLoginResponse invalidateLoginResponse=new InvalidateLoginResponse();
					InvalidationLogin invalidationLogin=invalidationLoginRepository.findByUsernameAndIsvalidate(username,true);
					if(invalidationLogin!=null) {
						basicApiResponse.setHttpstatus(HttpStatus.BAD_REQUEST);
						basicApiResponse.setMessage("Not Authorized");
						basicApiResponse.setStatus(false);
						invalidateLoginResponse.getInvalidateResponse(basicApiResponse);
						return;
						
					}
				}
			} catch (ExpiredJwtException | IllegalArgumentException e) {
				e.printStackTrace();
			} 
		} else {
			chain.doFilter(request, response);
			return;
		}

		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

			
		UserDetails adminDetails = this.userDetailsService.loadUserByAdminname(username);
		
		Constant.USERNAMEFROMTOKEN= adminDetails.getUsername();
		
			if (!ObjectUtils.isEmpty(adminDetails) && jwtTokenUtil.validateToken(jwtToken, adminDetails)) {

				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
						adminDetails, null, adminDetails.getAuthorities());
				usernamePasswordAuthenticationToken
						.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

			}
		}
		chain.doFilter(request, response);
	}

}