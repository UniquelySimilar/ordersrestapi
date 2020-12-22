package com.tcoveney.ordersrestapi.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.tcoveney.ordersrestapi.model.Product;

@Repository
@Transactional
public class ProductDaoHibernateImpl implements ProductDao {
	private SessionFactory sessionFactory;
	
	ProductDaoHibernateImpl(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Product> findAll() {
		Session session = sessionFactory.getCurrentSession();
		return session.createQuery("from Product").list();
	}

	@Override
	public Product find(int id) {
		// TODO Auto-generated method stub
		return null;
	}

}
