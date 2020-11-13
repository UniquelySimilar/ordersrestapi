package com.tcoveney.ordersrestapi.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.tcoveney.ordersrestapi.model.LineItem;

@Repository
@Transactional
public class LineItemDaoHibernateImpl implements LineItemDao {
	private SessionFactory sessionFactory;
	
	public LineItemDaoHibernateImpl(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<LineItem> findAll() {
		Session session = sessionFactory.getCurrentSession();
		return session.createQuery("from LineItem").list();
	}

	@Override
	public int insert(LineItem lineItem) {
		Session session = sessionFactory.getCurrentSession();
		return (Integer)session.save(lineItem);
	}

	@Override
	public void update(LineItem lineItem) {
		Session session = sessionFactory.getCurrentSession();
		session.update(lineItem);
	}

	@Override
	public void delete(int id) {
		Session session = sessionFactory.getCurrentSession();
		LineItem lineItem = session.load(LineItem.class, id);
		session.delete(lineItem);
	}
	
}
