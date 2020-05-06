package com.tcoveney.ordersrestapi.controller;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
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
	public String login(@RequestBody TokenUser tokenUser, HttpServletResponse response) {
		logger.debug("called 'login()'");
		try {
			TokenUser user = tokenUserDao.find(tokenUser.getUserName(), tokenUser.getPassword());
		}
		catch (DataAccessException dae) {
			System.err.print(dae);
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return "Unable to login. Contact admin.";
			
			//return new ResponseEntity<String>("Unable to login. Contact admin.", responseHeaders, HttpStatus.CREATED);
		}
		
		// TODO: Retrieve token and set to user
		// TODO: Calculate expiration timestamp and set to user
		// TODO: Return status code
		
		return "success";
	}

}
