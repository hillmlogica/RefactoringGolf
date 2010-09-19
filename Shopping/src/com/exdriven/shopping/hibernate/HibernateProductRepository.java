package com.exdriven.shopping.hibernate;

import java.util.Collection;

import org.hibernate.SessionFactory;

import com.exdriven.shopping.Product;
import com.exdriven.shopping.ProductRepository;

public class HibernateProductRepository implements ProductRepository {
	private final SessionFactory sessionFactory;

	public HibernateProductRepository(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@SuppressWarnings("unchecked")
	public Collection<Product> allProducts() {
		return sessionFactory.getCurrentSession().createCriteria(Product.class).list();
	}

	public Product loadProduct(int productId) {
		return (Product) sessionFactory.getCurrentSession().load(Product.class, Integer.valueOf(productId));
	}

	public void save(Product product) {
		sessionFactory.getCurrentSession().save(product);
	}

}
