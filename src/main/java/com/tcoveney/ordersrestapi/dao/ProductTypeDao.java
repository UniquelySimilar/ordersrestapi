package com.tcoveney.ordersrestapi.dao;

import java.util.List;

import com.tcoveney.ordersrestapi.model.ProductType;

public interface ProductTypeDao {
	public List<ProductType> findAll();
}
