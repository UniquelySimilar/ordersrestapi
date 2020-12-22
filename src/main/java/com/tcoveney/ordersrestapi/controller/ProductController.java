package com.tcoveney.ordersrestapi.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tcoveney.ordersrestapi.dao.ProductDao;
import com.tcoveney.ordersrestapi.model.Product;

@RestController
@RequestMapping("/api/products")
//"http://localhost:9000" - vue cli dev server
//"http://vue-client-for-spring-rest.localhost" - Apache2 virtualhost for vue client
@CrossOrigin(origins = {"http://localhost:9000", "http://vue-client-for-spring-rest.localhost"})
public class ProductController {
	private ProductDao productDao;
	
	ProductController(ProductDao productDao) {
		this.productDao = productDao;
	}
	
	@GetMapping("/")
	public List<Product> findAll() {
		return productDao.findAll();
	}
	
	@GetMapping("/{id}")
	public Product find(@PathVariable int id) {
		return productDao.find(id);		
	}

}
