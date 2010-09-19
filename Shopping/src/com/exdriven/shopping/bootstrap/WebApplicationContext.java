package com.exdriven.shopping.bootstrap;

import org.hibernate.SessionFactory;

import com.exdriven.shopping.ProductRepository;
import com.exdriven.shopping.XmlOrderService;
import com.exdriven.shopping.hibernate.HibernateProductRepository;
import com.exdriven.shopping.hibernate.HibernateSessionFactoryBuilder;
import com.exdriven.shopping.hibernate.HibernateUserRepository;


public class WebApplicationContext {
	private static WebApplicationContext instance;

	private HibernateUserRepository userRepository;
	private HibernateProductRepository productRepository;
	private SessionFactory sessionFactory;
	private XmlOrderService orderService = new XmlOrderService() {
		public void send(@SuppressWarnings("unused") String orderAsXml) {
		}
	};
	
	public static WebApplicationContext instance() {
		if (instance == null) {
			instance = new WebApplicationContext();
		}
		return instance;
	}
	
	public HibernateUserRepository userRepository() {
		if (userRepository == null) {
			userRepository = new HibernateUserRepository(sessionFactory());
		}
		return userRepository;
	}

	public ProductRepository productRepository() {
		if (productRepository == null) {
			productRepository = new HibernateProductRepository(sessionFactory());
		}
		return productRepository;
	}

	public SessionFactory sessionFactory() {
		if (sessionFactory == null) {
			sessionFactory = new HibernateSessionFactoryBuilder().build();
		}
		return sessionFactory;
	}

	public XmlOrderService orderService() {
		return orderService;
	}
	
	public void setOrderService(XmlOrderService orderService) {
		this.orderService = orderService;
	}
}
