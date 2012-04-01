package dk.itu.realms.application.impl;

import java.io.Serializable;
import java.util.Collection;

import javax.annotation.Resource;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import dk.itu.realms.application.AuthenticationService;

@Service("authenticationService")
public class AuthenticationServiceImpl implements dk.itu.realms.application.AuthenticationService, Serializable {

	private static final long serialVersionUID = 5130555875639678587L;
	
	@Resource(name = "authenticationManager")
	private AuthenticationManager authenticationManager; /* specific for Spring Security */

	/**
	 * @see AuthenticationService#login(String, String)
	 */
	@Override
	public boolean login(String username, String password) {
		try {
			Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
			if (authenticate.isAuthenticated()) {
				SecurityContextHolder.getContext().setAuthentication(authenticate);				
				return true;
			}
		} catch (AuthenticationException e) {			
		}
		return false;
	}

	/**
	 * @see AuthenticationService#logout()
	 */
	@Override
	public void logout() {
		SecurityContextHolder.getContext().setAuthentication(null);
	}

	/**
	 * @see AuthenticationService#getLoginID()
	 */
	@Override
	public String getLoginID() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		if(auth != null) {
			return auth.getName();
		} else {
			return "";
		}
	}
	
	/**
	 * @see AuthenticationService#isLoggedIn()
	 */
	@Override
	public boolean isLoggedIn() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		return auth.isAuthenticated() && ! ANONYMOUS.equals(auth.getName());
	}
	
	/**
	 * @see AuthenticationService#hasRole(String)
	 */
	@Override
	public boolean hasRole(String role) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Collection<GrantedAuthority> authorities = auth.getAuthorities();
		
		for(GrantedAuthority ga: authorities) {
			if(ga.getAuthority().equals(role)) {
				return true;
			}
		}
		
		return false;
	}
}
