package com.tcoveney.ordersrestapi.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
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
import com.tcoveney.ordersrestapi.dao.ProductTypeDao;
import com.tcoveney.ordersrestapi.model.Product;
import com.tcoveney.ordersrestapi.model.ProductType;
import com.tcoveney.ordersrestapi.validator.ValidationUtils;

@RestController
@RequestMapping("/api/products")
//"http://localhost:9000" - vue cli dev server
//"http://vue-client-for-spring-rest.localhost" - Apache2 virtualhost for vue client
@CrossOrigin(origins = {"http://localhost:9000", "http://vue-client-for-spring-rest.localhost"})
public class ProductController {
	private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

	private ProductDao productDao;
	private ProductTypeDao productTypeDao;
	private ValidationUtils validationUtils;
	
	ProductController(ProductDao productDao, ProductTypeDao productTypeDao, ValidationUtils validationUtils) {
		this.productDao = productDao;
		this.productTypeDao = productTypeDao;
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
				validationUtils.createDataIntegrityViolationResponse("name", "Name must be unique", response);
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
				validationUtils.createDataIntegrityViolationResponse("name", "Name must be unique", response);
			}
		}
	}
	
	@DeleteMapping("/{id}")
	public void delete(@PathVariable int id, HttpServletResponse response) {
		// TODO: Verify that this product is not associated with any line items.  If so, abort and send message to user.
		try {
			productDao.delete(id);
		}
		// Process constraint violation when product is associated with a line item.
		catch (DataIntegrityViolationException dive) {
			validationUtils.createDataIntegrityViolationResponse("warning",
					"Product cannot be deleted when associated with a line item", response);
		}
	}
	
	// Create test data
	@GetMapping("/testdata")
	public void insertTestData(HttpServletResponse response) {
		List<Product> products = productDao.findAll();
		if (!products.isEmpty()){
			// Test data already exists
			logger.warn("Product data already exists");
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			String responseBody = "{\"warning\":\"Product data already exists\"}";
			try {
				response.getWriter().write(responseBody);
				response.getWriter().flush();
			}
			catch(IOException ioe) {
				logger.error("Error writing to response", ioe);
			}
			return;
		}
		
		List<ProductType> productTypeList = productTypeDao.findAll();
		productTypeList.forEach( productType -> {
			for (int i = 1; i < 21; i++) {
				String productName = productType.getName() + i;
				Product product = new Product();
				product.setName(productName);
				product.setDescription(productName + " description");
				product.setUnitPrice(new BigDecimal(i + ".99"));
				product.setProductType(productType);
				productDao.insert(product);
			}
		});
	}

}
