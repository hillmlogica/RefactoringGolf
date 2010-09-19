package com.exdriven.shopping.hibernate;

import org.hibernate.SessionFactory;

import com.exdriven.shopping.hibernate.SessionFactoryWrapper.SessionClosure;

public class CrudTestCase {
	private static SessionFactory singletonSessionFactory;
	private SessionFactoryWrapper sessionFactoryWrapper = new SessionFactoryWrapper(singletonSessionFactory());
	
	public void inASession(SessionClosure work) {
		sessionFactoryWrapper.inASession(work);
	}
	
	public void save(Object... objects) {
		sessionFactoryWrapper.save(objects);
	}
	
	public SessionFactory sessionFactory() {
		return singletonSessionFactory();
	}

	private static SessionFactory singletonSessionFactory() {
		if (singletonSessionFactory == null) {
			singletonSessionFactory = new HibernateSessionFactoryBuilder().build();
		}
		return singletonSessionFactory;
	}

}
