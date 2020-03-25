package com.tcoveney.ordersrestapi.dao;

import java.util.List;

import com.tcoveney.ordersrestapi.model.Order;

public interface OrderDao {
	public List<Order> findAll();
	public Order find(int id);
	public List<Order> findByCustomer(int customerId);
	public int insert(Order order);
	public void update(Order order);
	public void delete(int id);
}
