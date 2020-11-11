package com.tcoveney.ordersrestapi.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tcoveney.ordersrestapi.dao.LineItemDao;
import com.tcoveney.ordersrestapi.model.LineItem;

@RestController
@RequestMapping("/api/lineitems")
// "http://localhost:9000" - vue cli dev server
// "http://vue-client-for-spring-rest.localhost" - Apache2 virtualhost for vue client
@CrossOrigin(origins = {"http://localhost:9000", "http://vue-client-for-spring-rest.localhost"})
public class LineItemController {
	private LineItemDao lineItemDao;
	
	public LineItemController(LineItemDao lineItemDao) {
		this.lineItemDao = lineItemDao;
	}
	
	@GetMapping("/")
	List<LineItem> findAll() {
		return null;
	}

}
