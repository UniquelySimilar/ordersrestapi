package com.tcoveney.ordersrestapi.controller;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tcoveney.ordersrestapi.service.TokenUserService;

@RestController
public class LoginController {
	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

	@Autowired
	private TokenUserService tokenUserService;

	@PostMapping("/login")
	public String login(@RequestParam("username") final String username,
			@RequestParam("password") final String password) {
		String token = tokenUserService.login(username, password);
		if (StringUtils.isEmpty(token)) {
			return "User could not be logged in. Contact Admin.";
		}

		return token;
	}
}
