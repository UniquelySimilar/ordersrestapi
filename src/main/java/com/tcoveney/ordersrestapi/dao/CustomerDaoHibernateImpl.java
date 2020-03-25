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
public class CustomerDaoHibernateImpl implements CustomerDao {
	private static final Logger logger = LogManager.getLogger(CustomerDaoHibernateImpl.class);
    private SessionFactory sessionFactory;

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

	@Override
	@SuppressWarnings("unchecked")
	public List<Customer> findAll() {
		Session session = sessionFactory.getCurrentSession();
		return session.createQuery("from Customer").list();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Customer> findAllOrderByLastName() {
		Session session = sessionFactory.getCurrentSession();
		return session.createQuery("from Customer order by lastName").list();
	}

	@Override
	public Customer find(int id) {
		Session session = sessionFactory.getCurrentSession();
		return (Customer)session.get(Customer.class, id);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Customer> findByLastName(String lastName) {
		Session session = sessionFactory.getCurrentSession();
		String hql = "from Customer where lastName = :last_name";
		return session.createQuery(hql).setParameter("last_name", lastName).list();
	}

	@Override
	public int insert(Customer customer) {
		Session session = sessionFactory.getCurrentSession();
		return (Integer)session.save(customer);
	}

	@Override
	public void update(Customer customer) {
		Session session = sessionFactory.getCurrentSession();
		customer.setUpdatedAt(new Date());
		// NOTE: When using 'merge()' instead of 'update()', if record with this customer.id did not exist,
		// a new record was created, which is NOT what I want here.
		session.update(customer);
	}

	@Override
	public void delete(int id) {
		Session session = sessionFactory.getCurrentSession();
		Customer customer = new Customer();
		customer.setId(id);
		session.delete(customer);
	}
}
