package com.exdriven.shopping.hibernate;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import com.exdriven.shopping.User;
import com.exdriven.shopping.UserRepository;

public class HibernateUserRepository implements UserRepository {
	private final SessionFactory sessionFactory;

	public HibernateUserRepository(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public User findUserByUsername(String username) {
		return (User) sessionFactory.getCurrentSession()
					.createCriteria(User.class)
					.add(Restrictions.eq("username", username))
					.uniqueResult();
	}

	public void save(User user) {
		sessionFactory.getCurrentSession().save(user);
	}

}
