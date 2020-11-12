package com.tcoveney.ordersrestapi.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tcoveney.ordersrestapi.dao.LineItemDao;
import com.tcoveney.ordersrestapi.model.LineItem;
import com.tcoveney.ordersrestapi.validator.ValidationUtils;

@RestController
@RequestMapping("/api/lineitems")
// "http://localhost:9000" - vue cli dev server
// "http://vue-client-for-spring-rest.localhost" - Apache2 virtualhost for vue client
@CrossOrigin(origins = {"http://localhost:9000", "http://vue-client-for-spring-rest.localhost"})
public class LineItemController {
	private LineItemDao lineItemDao;
	private ValidationUtils validationUtils;
	
	public LineItemController(LineItemDao lineItemDao, ValidationUtils validationUtils) {
		this.lineItemDao = lineItemDao;
		this.validationUtils = validationUtils;
	}
	
	@GetMapping("/")
	List<LineItem> findAll() {
		return lineItemDao.findAll();
	}
	
	@PostMapping(value = "/", consumes = "application/json")
	public void insert(@RequestBody @Valid LineItem lineItem, BindingResult bindingResult, HttpServletRequest request, HttpServletResponse response) {
		if (bindingResult.hasErrors()) {
			validationUtils.createValidationErrorsResponse(bindingResult, response);
		}
		else {
			int newLineItemId = lineItemDao.insert(lineItem);
			response.setStatus(201);
			response.addHeader( "Location", request.getRequestURL().append( Integer.toString(newLineItemId) ).toString() );
		}
	}

}
