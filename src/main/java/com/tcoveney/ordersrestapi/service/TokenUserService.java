package com.tcoveney.ordersrestapi.service;

import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import com.tcoveney.ordersrestapi.dao.TokenUserDao;
import com.tcoveney.ordersrestapi.model.TokenUser;

@Service
public class TokenUserService {
	private static final Logger logger = LoggerFactory.getLogger(TokenUserService.class);
	
	@Autowired
	private TokenUserDao tokenUserDao;
	
	public String login(String username, String password) {
		String retVal = "";
		TokenUser tokenUser = tokenUserDao.find(username, password);
		if (null != tokenUser) {
			String token = UUID.randomUUID().toString();
			tokenUser.setToken(token);
			tokenUserDao.update(tokenUser);
			retVal = token;
		}
		
		return retVal;
	}

	public Optional<User> findByToken(String token) {
		TokenUser tokenUser = tokenUserDao.findByToken(token);
		if(null != tokenUser){
			User user= new User(tokenUser.getUserName(), tokenUser.getPassword(), true, true, true, true,
					AuthorityUtils.createAuthorityList("USER"));
			return Optional.of(user);
        }

		return  Optional.empty();
	}

}
