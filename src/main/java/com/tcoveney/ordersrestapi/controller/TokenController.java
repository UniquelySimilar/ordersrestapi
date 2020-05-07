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
public class TokenController {
	private static final Logger logger = LoggerFactory.getLogger(TokenController.class);

	@Autowired
	private TokenUserService tokenUserService;

	@PostMapping("/token")
	public String getToken(@RequestParam("username") final String username,
			@RequestParam("password") final String password) {
		String token = tokenUserService.login(username, password);
		if (StringUtils.isEmpty(token)) {
			return "Token could not be obtained. Contact Admin.";
		}

		return token;
	}
}
