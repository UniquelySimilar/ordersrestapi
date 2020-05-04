package com.tcoveney.ordersrestapi.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tcoveney.ordersrestapi.dao.TokenUserDao;
import com.tcoveney.ordersrestapi.dao.TokenUserDaoHibernateImpl;
import com.tcoveney.ordersrestapi.model.TokenUser;

@RestController
@RequestMapping("/api/token")
public class TokenUserController {
	private static final Logger logger = LoggerFactory.getLogger(TokenUserController.class);

	@Autowired
	private TokenUserDao tokenUserDao;
	
	@PostMapping("/login")
	public String login(@RequestBody TokenUser tokenUser) {
		logger.debug("called 'login()'");
		TokenUser user = tokenUserDao.find(tokenUser.getUserName());
		
		// TODO: Possibly catch except NoResultException or NonUniqueResultException and convert to http status code
		// TODO: Retrieve token and set to user
		// TODO: Calculate expiration timestamp and set to user
		// TODO: Return status code
		
		return "success";
	}

}
