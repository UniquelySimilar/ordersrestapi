package com.tcoveney.ordersrestapi.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.tcoveney.ordersrestapi.model.ProductType;

@Repository
@Transactional
public class ProductTypeDaoHibernateImpl implements ProductTypeDao {
	private SessionFactory sessionFactory;

	public ProductTypeDaoHibernateImpl(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<ProductType> findAll() {
		Session session = sessionFactory.getCurrentSession();
		return session.createQuery("from ProductType").list();
	}

}
