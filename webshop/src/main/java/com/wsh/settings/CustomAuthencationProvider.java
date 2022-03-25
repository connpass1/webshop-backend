package com.wsh.settings;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.wsh.repo.RepositoryUser;

@Component
public class CustomAuthencationProvider implements AuthenticationProvider {
	@Autowired
	private Logger log;
	@Autowired
	private RepositoryUser repo;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {

		String userName = authentication.getName();
		String password = authentication.getCredentials().toString();
		log.error(password);
		com.wsh.model.User myUser = repo.findByName(userName);
		if (myUser == null) {
			throw new BadCredentialsException("Unknown user - " + userName);
		}
		if (!password.equals(myUser.getPassword())) {
			throw new BadCredentialsException("Bad password ");
		}
		UserDetails principal = User.builder().username(myUser.getName()).password(myUser.getPassword())
				.roles(myUser.getRole()).build();

		return new UsernamePasswordAuthenticationToken(principal, password, principal.getAuthorities());

	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}
}