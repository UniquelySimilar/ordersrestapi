package com.tcoveney.ordersrestapi.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tcoveney.ordersrestapi.dao.ProductTypeDao;
import com.tcoveney.ordersrestapi.model.ProductType;

@RestController
@RequestMapping("/api/producttypes")
//"http://localhost:9000" - vue cli dev server
//"http://vue-client-for-spring-rest.localhost" - Apache2 virtualhost for vue client
@CrossOrigin(origins = {"http://localhost:9000", "http://vue-client-for-spring-rest.localhost"})
public class ProductTypeController {
	private ProductTypeDao productTypeDao;
	
	public ProductTypeController(ProductTypeDao productTypeDao) {
		this.productTypeDao = productTypeDao;
	}

	@GetMapping("/")
	public List<ProductType> findAll() {
		return productTypeDao.findAll();
	}

}
