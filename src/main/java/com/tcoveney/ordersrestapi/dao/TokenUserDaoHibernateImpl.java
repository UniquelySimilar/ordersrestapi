package com.tcoveney.ordersrestapi.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.tcoveney.ordersrestapi.model.TokenUser;

@Repository
@Transactional
public class TokenUserDaoHibernateImpl implements TokenUserDao {
	private static final Logger logger = LoggerFactory.getLogger(TokenUserDaoHibernateImpl.class);
    private SessionFactory sessionFactory;

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

	@Override
	public TokenUser find(String userName) {
		// TODO: Determine how to handle NoResultException.
		// Trying to throw it from here to controller did not work.
		Session session = sessionFactory.getCurrentSession();
		String hql = "from TokenUser where userName = :userName";
		return (TokenUser)session.createQuery(hql).setParameter("userName", userName).getSingleResult();
	}

	@Override
	public TokenUser findByToken(String token) {
		Session session = sessionFactory.getCurrentSession();
		String hql = "from TokenUser where token = :token";
		return (TokenUser)session.createQuery(hql).setParameter("token", token).getSingleResult();
	}

}
