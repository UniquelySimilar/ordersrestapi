package com.tcoveney.ordersrestapi.dao;

import java.util.List;

import com.tcoveney.ordersrestapi.model.Product;

public interface ProductDao {
	public List<Product> findAll();
	public Product find(int id);
	public int insert(Product product);
	public void update(Product product);
	public void delete(int id);
}
