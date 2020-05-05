package com.tcoveney.ordersrestapi.service;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tcoveney.ordersrestapi.dao.TokenUserDao;
import com.tcoveney.ordersrestapi.model.TokenUser;

@Service
public class LoginService {
	private static final Logger logger = LoggerFactory.getLogger(LoginService.class);
	
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

}
