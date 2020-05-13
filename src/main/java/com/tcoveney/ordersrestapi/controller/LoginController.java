package com.tcoveney.ordersrestapi.controller;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tcoveney.ordersrestapi.model.TokenUser;
import com.tcoveney.ordersrestapi.service.TokenUserService;

@RestController
@RequestMapping("/login")
//"http://localhost:9000" - vue cli dev server
//"http://vue-client-for-spring-rest.localhost" - Apache2 virtualhost for vue client
@CrossOrigin(origins = {"http://localhost:9000", "http://vue-client-for-spring-rest.localhost"})
public class LoginController {
	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

	@Autowired
	private TokenUserService tokenUserService;

	@PostMapping("/login")
	public String login(@RequestBody TokenUser tokenUser) {
		String token = tokenUserService.login(tokenUser.getUsername(), tokenUser.getPassword());
		if (StringUtils.isEmpty(token)) {
			// TODO: Return 4xx response code if user not logged in
			return "User could not be logged in. Contact Admin.";
		}

		return token;
	}
}
