package com.tcoveney.ordersrestapi.dao;

import java.util.List;

import com.tcoveney.ordersrestapi.model.Customer;

public interface CustomerDao {
	public List<Customer> findAll();
	public List<Customer> findAllOrderByLastName();
	public Customer find(int id);
	public List<Customer> findByLastName(String lastName);
	public int insert(Customer customer);
	public void update(Customer customer);
	public void delete(int id);
}
