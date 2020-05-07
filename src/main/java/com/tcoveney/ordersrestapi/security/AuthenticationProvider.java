package com.tcoveney.ordersrestapi.security;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.tcoveney.ordersrestapi.service.TokenUserService;

@Component
public class AuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {
	private static final Logger logger = LoggerFactory.getLogger(AuthenticationProvider.class);

	@Autowired
	private TokenUserService tokenUserService;

	@Override
	protected void additionalAuthenticationChecks(UserDetails arg0, UsernamePasswordAuthenticationToken arg1)
			throws AuthenticationException {
		// TODO Auto-generated method stub

	}

	@Override
	protected UserDetails retrieveUser(String userName, UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken)
			throws AuthenticationException {
		//logger.debug("called 'retrieveUser()'");
		Object token = usernamePasswordAuthenticationToken.getCredentials();
		return Optional
				.ofNullable(token)
				.map(String::valueOf)
				.flatMap(tokenUserService::findByToken)
				.orElseThrow(() -> new UsernameNotFoundException("Cannot find user with authentication token=" + token));
	}

}
