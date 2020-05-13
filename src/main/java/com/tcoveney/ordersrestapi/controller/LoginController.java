package com.tcoveney.ordersrestapi.controller;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tcoveney.ordersrestapi.ResponseInfo;
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
	public ResponseEntity<ResponseInfo> login(@RequestBody TokenUser tokenUser) {
		ResponseInfo responseInfo = new ResponseInfo();
		HttpStatus httpStatus = HttpStatus.OK;
		String token = tokenUserService.login(tokenUser.getUsername(), tokenUser.getPassword());
		if (!StringUtils.isEmpty(token)) {
			responseInfo.setMessage(token);
		}
		else {
			responseInfo.setError("Unauthorized");
			responseInfo.setMessage("User could not be logged in. Contact Admin.");
			httpStatus = HttpStatus.UNAUTHORIZED;
		}
		
		responseInfo.setStatus(httpStatus.value());
		return new ResponseEntity<ResponseInfo>(responseInfo, httpStatus);
	}
}
