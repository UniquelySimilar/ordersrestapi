package com.tcoveney.ordersrestapi.dao;

import java.util.List;

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
	@SuppressWarnings("unchecked")
	public TokenUser find(String username, String password) {
		TokenUser tokenUser = null;
		Session session = sessionFactory.getCurrentSession();
		String hql = "from TokenUser where username = :username and password = :password";
		List<TokenUser> tokenUsers = session.createQuery(hql)
				.setParameter("username", username)
				.setParameter("password", password)
				.list();
		if (tokenUsers.size() == 1) {
			tokenUser = tokenUsers.get(0);
		}
		
		return tokenUser;
	}

	@Override
	public TokenUser findByToken(String token) {
		Session session = sessionFactory.getCurrentSession();
		String hql = "from TokenUser where token = :token";
		return (TokenUser)session.createQuery(hql).setParameter("token", token).getSingleResult();
	}

	@Override
	public void update(TokenUser tokenUser) {
		Session session = sessionFactory.getCurrentSession();
		session.update(tokenUser);
	}

}
