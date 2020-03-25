package com.tcoveney.ordersrestapi.model;

import java.util.ArrayList;
import java.util.List;

public class CustomerWithOrders {
	// This is a utility class to return a Customer with Orders to a REST client.
	// Trying to create a bi-directional association between Customer and Order with @OneToMany was creating problems,
	// and online articles by Hibernate/JPA experts tend to not recommend it.
	private Customer customer;
	private List<Order> orders = new ArrayList<Order>();
	
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public List<Order> getOrders() {
		return orders;
	}
	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}
}
