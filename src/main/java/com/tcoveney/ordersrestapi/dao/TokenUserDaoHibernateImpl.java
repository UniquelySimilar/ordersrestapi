package com.tcoveney.ordersrestapi.dao;

import java.util.List;
import java.util.Optional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.tcoveney.ordersrestapi.model.TokenUser;

@Repository
@Transactional
public class TokenUserDaoHibernateImpl implements TokenUserDao {
	private static final Logger logger = LoggerFactory.getLogger(TokenUserDaoHibernateImpl.class);
    private SessionFactory sessionFactory;
    private BCryptPasswordEncoder pwdEncoder = new BCryptPasswordEncoder();

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

	@Override
	@SuppressWarnings("unchecked")
	public TokenUser find(String username, String password) {
		TokenUser tokenUser = null;
		Session session = sessionFactory.getCurrentSession();
		String hql = "from TokenUser where username = :username";
		List<TokenUser> tokenUsers = session.createQuery(hql)
				.setParameter("username", username)
				.list();
		if (tokenUsers.size() == 1) {
			// username found
			tokenUser = tokenUsers.get(0);
			// Compare password
			if (!pwdEncoder.matches(password, tokenUser.getPassword())) {
				tokenUser = null;
				logger.warn("Password incorrect for user " + username);
			}
		}
		else {
			logger.warn("Expecting to find one matching user for username " +  username + ", but found " + tokenUsers.size());
		}
		
		return tokenUser;
	}

	@Override
	@SuppressWarnings("unchecked")
	public TokenUser findByToken(String token) {
		TokenUser tokenUser = null;
		Session session = sessionFactory.getCurrentSession();
		String hql = "from TokenUser where token = :token";
		List<TokenUser> tokenUsers = session.createQuery(hql).setParameter("token", token).list();
		if (tokenUsers.size() == 1) {
			tokenUser = tokenUsers.get(0);
		}
		
		return tokenUser;
	}

	@Override
	public void update(TokenUser tokenUser) {
		Session session = sessionFactory.getCurrentSession();
		session.update(tokenUser);
	}

}
