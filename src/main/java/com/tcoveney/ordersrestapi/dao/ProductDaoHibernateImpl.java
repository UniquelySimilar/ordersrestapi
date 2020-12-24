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
		Session session = sessionFactory.getCurrentSession();
		return (Product)session.get(Product.class, id);
	}

	@Override
	public int insert(Product product) {
		Session session = sessionFactory.getCurrentSession();
		return (Integer)session.save(product);
	}

	@Override
	public void update(Product product) {
		Session session = sessionFactory.getCurrentSession();
		// NOTE: When using 'merge()' instead of 'update()', if a record with this id does not exist,
		// a new record is created, which is NOT what I want.
		session.update(product);
	}

	@Override
	public void delete(int id) {
		Session session = sessionFactory.getCurrentSession();
		Product product = session.load(Product.class, id);
		session.delete(product);
	}

}
