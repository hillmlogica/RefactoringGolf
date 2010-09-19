package com.exdriven.shopping.hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class SessionFactoryWrapper {
	private final SessionFactory sessionFactory;
	
	public SessionFactoryWrapper(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	public void inASession(SessionClosure work) {
		Session session = this.sessionFactory.getCurrentSession();
		Transaction transaction = session.beginTransaction();
		try {
			work.run(session);
			transaction.commit();
		} catch (Throwable e) {
			if (transaction.isActive()) {
				transaction.rollback();
			}
			throw new UnitOfWorkException("Unexpected error in unit of work", e);
		}
	}
	
	public void save(final Object... objects) {
		inASession(new SessionClosure() {
			public void run(Session session) {
				for (Object object : objects) {
					session.save(object);
				}
			}
		});
	}
	
	public interface SessionClosure {
		void run(Session session);
	}
	
	public static class UnitOfWorkException extends RuntimeException {
		public UnitOfWorkException(String message, Throwable cause) {
			super(message, cause);
		}
	}
}
