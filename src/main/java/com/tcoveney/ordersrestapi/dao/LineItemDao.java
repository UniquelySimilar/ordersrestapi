package com.tcoveney.ordersrestapi.dao;

import java.util.List;

import com.tcoveney.ordersrestapi.model.LineItem;

public interface LineItemDao {
	List<LineItem> findAll();
	LineItem find(int id);
	List<LineItem> findByOrder(int orderId);
	int insert(LineItem lineItem);
	void update(LineItem lineItem);
	void delete(int id);
}
