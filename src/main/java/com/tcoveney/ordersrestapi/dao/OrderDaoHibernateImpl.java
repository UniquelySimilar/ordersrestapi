package com.tcoveney.ordersrestapi.dao;

import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.tcoveney.ordersrestapi.model.Customer;
import com.tcoveney.ordersrestapi.model.Order;

@Repository
@Transactional
public class OrderDaoHibernateImpl implements OrderDao {
	public static final Logger logger = LogManager.getLogger(OrderDaoHibernateImpl.class);
	private SessionFactory sessionFactory;

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

	@Override
	@SuppressWarnings("unchecked")
	public List<Order> findAll() {
		Session session = sessionFactory.getCurrentSession();
		return session.createQuery("from Order").list();
	}

	@Override
	public Order find(int id) {
		Session session = sessionFactory.getCurrentSession();
		return session.get(Order.class, id);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Order> findByCustomer(int customerId) {
		Session session = sessionFactory.getCurrentSession();
		return session.createQuery("from Order where customerId = :customerId").setParameter("customerId", customerId).list();
	}

	@Override
	public int insert(Order order) {
		Session session = sessionFactory.getCurrentSession();
		return (Integer)session.save(order);
	}

	@Override
	public void update(Order order) {
		Session session = sessionFactory.getCurrentSession();
		order.setUpdatedAt(new Date());
		session.update(order);
	}

	@Override
	public void delete(int id) {
		Session session = sessionFactory.getCurrentSession();
		Order order = new Order();
		order.setId(id);
		session.delete(order);
	}

}
