package com.tcoveney.ordersrestapi.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import com.tcoveney.ordersrestapi.dao.CustomerDao;
import com.tcoveney.ordersrestapi.dao.OrderDao;
import com.tcoveney.ordersrestapi.exception.ResourceNotFoundException;
import com.tcoveney.ordersrestapi.model.Customer;
import com.tcoveney.ordersrestapi.model.Order;
import com.tcoveney.ordersrestapi.validator.ValidationUtils;

@RestController
@RequestMapping("/api/customers")
// "http://localhost:9000" - vue cli dev server
// "http://vue-client-for-spring-rest.localhost" - Apache2 virtualhost for vue client
@CrossOrigin(origins = {"http://localhost:9000", "http://vue-client-for-spring-rest.localhost"})
public class CustomerController {
	private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);

	private ValidationUtils validationUtils;
	private CustomerDao customerDao;
	private OrderDao orderDao;
	
	CustomerController(CustomerDao customerDao, OrderDao orderDao, ValidationUtils validationUtils) {
		this.customerDao = customerDao;
		this.orderDao = orderDao;
		this.validationUtils = validationUtils;
	}

	@GetMapping("/")
	public List<Customer> findAll() {
		//logger.debug("Called 'findAll()'");
		List<Customer> customers = customerDao.findAll();
		
		return customers;	
	}

	@GetMapping("/orderby/lastname")
	public List<Customer> findAllOrderByLastName() {
		
		List<Customer> customers = customerDao.findAllOrderByLastName();
		
		return customers;	
	}
	
	@GetMapping("/{id}")
	public Customer find(@PathVariable int id) {
		Customer customer = customerDao.find(id);
		if (null == customer) {
			throw new ResourceNotFoundException();
		}
		return customer;
	}
	
	@GetMapping("/lastname/{lastName}")
	public List<Customer> findByLastName(@PathVariable String lastName) {
		return customerDao.findByLastName(lastName);
	}
	
	@GetMapping("/{customerId}/orders")
	public Customer findWithOrders(@PathVariable int customerId, HttpServletResponse response){
		Customer customer = customerDao.find(customerId);
		if (null == customer) {
			throw new ResourceNotFoundException();
		}
		else {
			List<Order> orders = orderDao.findByCustomer(customerId);
			customer.setOrders(orders);
		}
		return customer;
	}
	
	@PostMapping(value = "/", headers = "content-type=application/json")
	public void insert(@RequestBody @Valid Customer customer, BindingResult bindingResult, HttpServletRequest request, HttpServletResponse response) {
		if (bindingResult.hasErrors()) {
			validationUtils.createValidationErrorsResponse(bindingResult, response);
		}
		else {
			int newCustomerId = customerDao.insert(customer);
			response.setStatus(201);
			response.addHeader( "Location", request.getRequestURL().append( Integer.toString(newCustomerId) ).toString() );
		}
	}
	
	@PutMapping(value = "/{id}", headers = "content-type=application/json")
	public void update(@PathVariable int id, @RequestBody @Valid Customer customer, BindingResult bindingResult, HttpServletRequest request, HttpServletResponse response) {
		if (bindingResult.hasErrors()) {
			validationUtils.createValidationErrorsResponse(bindingResult, response);
		}
		else {
			customerDao.update(customer);
		}
	}
	
	@DeleteMapping("/{id}")
	public void delete(@PathVariable int id) {
		customerDao.delete(id);
	}

}
