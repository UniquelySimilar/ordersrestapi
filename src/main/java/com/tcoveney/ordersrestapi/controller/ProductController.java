package com.tcoveney.ordersrestapi.controller;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
	private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);

	private ProductDao productDao;
	
	ProductController(ProductDao productDao) {
		this.productDao = productDao;
	}
	
	@GetMapping("/")
	public List<Product> findAll() {
		return productDao.findAll();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Product> find(@PathVariable int id) {
		Product product = productDao.find(id);
		Optional<Product> optProduct = Optional.ofNullable(product);
		return ResponseEntity.of(optProduct);
	}
	
	@PostMapping("/")
	public void insert(@RequestBody Product product, HttpServletResponse response) {
		// TODO: Validation
		productDao.insert(product);
		response.setStatus(HttpServletResponse.SC_CREATED);
	}

}
