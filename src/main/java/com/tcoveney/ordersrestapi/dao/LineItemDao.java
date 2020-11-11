package com.tcoveney.ordersrestapi.dao;

import java.util.List;

import com.tcoveney.ordersrestapi.model.LineItem;

public interface LineItemDao {
	List<LineItem> findAll();
}
