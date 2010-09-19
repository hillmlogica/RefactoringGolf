package com.exdriven.shopping.hibernate;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.junit.Test;

import com.exdriven.shopping.Product;
import com.exdriven.shopping.ProductBuilder;
import com.exdriven.shopping.hibernate.SessionFactoryWrapper.SessionClosure;

public class HibernateProductRepositoryTest extends CrudTestCase {
	private final Product product1 = new ProductBuilder().build();
	private final Product product2 = new ProductBuilder().build();
	private final HibernateProductRepository productRepository = new HibernateProductRepository(sessionFactory());

	@Test
	public void canListAllProducts() {
		save(product1, product2);
		
		final List<String> productNames = new ArrayList<String>();
		inASession(new SessionClosure() {
			public void run(@SuppressWarnings("unused") Session session) {
				for (Product product : productRepository.allProducts()) {
					productNames.add(product.getName());
				}
			}});
		
		assertTrue(productNames.contains(product1.getName()));
		assertTrue(productNames.contains(product2.getName()));
	}

	@Test
	public void canFindAProductById() {
		save(product1, product2);
		
		inASession(new SessionClosure() {
			public void run(@SuppressWarnings("unused") Session session) {
				Product loaded1 = productRepository.loadProduct(product1.getId());
				assertThat(loaded1.getName(), is(product1.getName()));
				Product loaded2 = productRepository.loadProduct(product2.getId());
				assertThat(loaded2.getName(), is(product2.getName()));
			}});
	}
}
