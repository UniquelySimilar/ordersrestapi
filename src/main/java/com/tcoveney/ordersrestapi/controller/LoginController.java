package com.tcoveney.ordersrestapi.controller;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.tcoveney.ordersrestapi.ResponseInfo;
import com.tcoveney.ordersrestapi.model.TokenUser;
import com.tcoveney.ordersrestapi.service.TokenUserService;

@RestController
//@RequestMapping("/login")
//"http://localhost:9000" - vue cli dev server
//"http://vue-client-for-spring-rest.localhost" - Apache2 virtualhost for vue client
@CrossOrigin(origins = {"http://localhost:9000", "http://vue-client-for-spring-rest.localhost"})
public class LoginController {
	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

	@Autowired
	private TokenUserService tokenUserService;

	@PostMapping("/login")
	public ResponseEntity<ResponseInfo> login(@RequestBody TokenUser tokenUser) {
		ResponseInfo responseInfo = new ResponseInfo();
		HttpStatus httpStatus = HttpStatus.OK;
		String token = tokenUserService.login(tokenUser.getUsername(), tokenUser.getPassword());
		if (!StringUtils.isEmpty(token)) {
			responseInfo.setMessage(token);
		}
		else {
			responseInfo.setError("Unauthorized");
			responseInfo.setMessage("Invalid username or password");
			httpStatus = HttpStatus.UNAUTHORIZED;
		}
		
		responseInfo.setStatus(httpStatus.value());
		return new ResponseEntity<ResponseInfo>(responseInfo, httpStatus);
	}

	@PutMapping("/api/logout")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void logout(@RequestHeader("Authorization") String headerValue) {
		String token = StringUtils.removeStart(headerValue, "Bearer").trim();
		TokenUser tokenUser = tokenUserService.findByTokenOnly(token);
		if (null != tokenUser) {
			tokenUser.setToken(null);
			tokenUser.setTokenExp(null);
			tokenUserService.update(tokenUser);
		}
	}
}
