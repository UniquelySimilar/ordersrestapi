package com.tcoveney.ordersrestapi.dao;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.tcoveney.ordersrestapi.model.LineItem;

@Repository
@Transactional
public class LineItemDaoHibernateImpl implements LineItemDao {

	@Override
	public List<LineItem> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

}
