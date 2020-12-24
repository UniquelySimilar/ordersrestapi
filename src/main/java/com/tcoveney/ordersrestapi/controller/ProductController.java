package com.tcoveney.ordersrestapi.controller;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tcoveney.ordersrestapi.dao.ProductDao;
import com.tcoveney.ordersrestapi.model.Product;
import com.tcoveney.ordersrestapi.validator.ValidationUtils;

@RestController
@RequestMapping("/api/products")
//"http://localhost:9000" - vue cli dev server
//"http://vue-client-for-spring-rest.localhost" - Apache2 virtualhost for vue client
@CrossOrigin(origins = {"http://localhost:9000", "http://vue-client-for-spring-rest.localhost"})
public class ProductController {
	private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);

	private ProductDao productDao;
	private ValidationUtils validationUtils;
	
	ProductController(ProductDao productDao, ValidationUtils validationUtils) {
		this.productDao = productDao;
		this.validationUtils = validationUtils;
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
	public void insert(@RequestBody @Valid Product product, BindingResult bindingResult, HttpServletRequest request, HttpServletResponse response) {
		if (bindingResult.hasErrors()) {
			validationUtils.createValidationErrorsResponse(bindingResult, response);
		}
		else {
			try {
				int newProductId = productDao.insert(product);
				response.setStatus(HttpServletResponse.SC_CREATED);
				response.addHeader( "Location", request.getRequestURL().append( Integer.toString(newProductId) ).toString() );
			}
			// Process unique constraint violation
			catch (DataIntegrityViolationException dive) {
				validationUtils.createUniqueViolationResponse("name", "Name must be unique", response);
			}
		}
	}
	
	@PutMapping("/")
	public void update(@RequestBody @Valid Product product, BindingResult bindingResult, HttpServletRequest request, HttpServletResponse response) {
		if (bindingResult.hasErrors()) {
			validationUtils.createValidationErrorsResponse(bindingResult, response);
		}
		else {
			try {
				productDao.update(product);
			}
			// Process unique constraint violation
			catch (DataIntegrityViolationException dive) {
				validationUtils.createUniqueViolationResponse("name", "Name must be unique", response);
			}
		}
	}
	
	@DeleteMapping("/{id}")
	public void delete(@PathVariable int id) {
		// TODO: Verify that this product is not associated with any line items.  If so, abort and send message to user.
		productDao.delete(id);
	}

}
