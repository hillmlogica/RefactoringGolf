package com.exdriven.shopping.hibernate;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

import org.hibernate.SessionFactory;
import org.hibernate.StaleObjectStateException;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.exdriven.shopping.bootstrap.WebApplicationContext;

public class HibernateSessionFilter implements Filter {

	private static Logger log = LoggerFactory.getLogger(HibernateSessionFilter.class);

	private SessionFactory sessionFactory;

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException {
		try {
			log.debug("Starting a database transaction");
			Transaction transaction = sessionFactory.getCurrentSession().beginTransaction();

			// Call the next filter (continue request processing)
			chain.doFilter(request, new HttpResponseWrapper(transaction, response));

			// Commit and cleanup
			if (transaction.isActive()) {
				log.debug("Committing the database transaction");
				sessionFactory.getCurrentSession().getTransaction().commit();
			}

		} catch (StaleObjectStateException staleEx) {
			log.error("This interceptor does not implement optimistic concurrency control!");
			log.error("Your application will not work until you add compensation actions!");
			// Rollback, close everything, possibly compensate for any permanent
			// changes
			// during the conversation, and finally restart business
			// conversation. Maybe
			// give the user of the application a chance to merge some of his
			// work with
			// fresh data... what you do here depends on your applications
			// design.
			throw staleEx;
		} catch (Throwable ex) {
			// Rollback only
			ex.printStackTrace();
			try {
				if (sessionFactory.getCurrentSession().getTransaction().isActive()) {
					log.debug("Trying to rollback database transaction after exception");
					sessionFactory.getCurrentSession().getTransaction().rollback();
				}
			} catch (Throwable rbEx) {
				log.error("Could not rollback transaction after exception!", rbEx);
			}

			// Let others handle it... maybe another interceptor for exceptions?
			throw new ServletException(ex);
		}
	}

	public void init(@SuppressWarnings("unused") FilterConfig filterConfig) {
		log.debug("Initializing filter...");
		log.debug("Obtaining SessionFactory from static HibernateUtil singleton");
		sessionFactory = WebApplicationContext.instance().sessionFactory();
	}

	public static class HttpResponseWrapper extends HttpServletResponseWrapper {
		private final Transaction transaction;

		public HttpResponseWrapper(Transaction transaction, ServletResponse response) {
			super((HttpServletResponse) response);
			this.transaction = transaction;
		}

		@Override
		public void sendRedirect(String location) throws IOException {
			transaction.commit();
			super.sendRedirect(location);
		}

	}

	public void destroy() {
	}

}
